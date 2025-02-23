package ecommerce.orderService.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.orderService.client.product.Product;
import ecommerce.orderService.client.product.ProductBulkDecreaseRequest;
import ecommerce.orderService.client.product.ProductBulkRequest;
import ecommerce.orderService.client.product.ProductClient;
import ecommerce.orderService.client.user.User;
import ecommerce.orderService.client.user.UserClient;
import ecommerce.orderService.exception.EntityNotFoundException;
import ecommerce.orderService.messaging.event.consume.ProductReservationFailedForOrderEvent;
import ecommerce.orderService.messaging.event.consume.ProductReservedForOrderEvent;
import ecommerce.orderService.messaging.event.publish.OrderReserveProductEvent;
import ecommerce.orderService.messaging.outbox.EventOutboxService;
import ecommerce.orderService.order.domain.Order;
import ecommerce.orderService.order.dto.OrderMapper;
import ecommerce.orderService.order.dto.OrderProductRequest;
import ecommerce.orderService.order.dto.OrderRequest;
import ecommerce.orderService.order.dto.OrderResponse;
import ecommerce.orderService.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductClient productClient;
	private final UserClient userClient;
	private final EventOutboxService eventOutboxService;

	/**
	 * 주문을 등록하고, 상품의 재고를 차감한 후 주문을 완료 처리하는 메서드입니다.
	 *
	 * @param orderRequest 주문 요청 정보
	 * @return 등록된 주문의 응답 정보
	 */
	public OrderResponse registerOrder(OrderRequest orderRequest) {
		// 주문 요청에 해당하는 사용자 정보 조회
		User user = userClient.getUserById(orderRequest.getUserSeq());

		// 주문에 포함된 상품 IDs를 추출하여 상품 목록을 조회
		List<Long> productIds = orderRequest.getOrderProductRequests()
			.stream()
			.map(OrderProductRequest::getProductId)
			.toList();

		// 상품 정보를 외부 서비스에서 조회
		List<Product> products = productClient.getAllProductsById(new ProductBulkRequest(productIds));

		// 주문 객체 생성 및 총 금액 계산
		Order order = OrderMapper.toOrder(orderRequest);
		order.calculateTotalAmount(products);

		// 재고 차감 요청 객체 생성
		ProductBulkDecreaseRequest productBulkDecreaseRequest = order.toBulkDecreaseRequest();

		// 상품 재고 차감 요청
		productClient.bulkDecreaseProduct(productBulkDecreaseRequest);

		// 주문 상태 완료 처리
		order.complete();

		// 주문 저장
		orderRepository.save(order);

		// 주문 응답 객체 반환
		return OrderResponse.from(order);
	}

	/**
	 * 주문을 취소하고, 상품 재고를 원상복구한 후 취소된 주문의 응답을 반환하는 메서드입니다.
	 *
	 * @param id 주문 ID
	 * @return 취소된 주문의 응답 정보
	 */
	public OrderResponse cancelOrder(Long id) {
		// 주문 조회, 없으면 예외 발생
		Order order = orderRepository.findByIdWithProducts(id)
			.orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found."));

		// 상품 재고를 원상복구하는 요청
		productClient.bulkIncreaseProduct(order.toProductBulkIncreaseRequest());

		// 주문 취소 처리
		order.cancel();

		// 주문 응답 객체 반환
		return OrderResponse.from(order);
	}

	/**
	 * 비동기 방식으로 주문을 등록하고, 주문 상태를 'pending' 으로 설정 후 이벤트를 발송하는 메서드입니다.
	 *
	 * @param orderRequest 주문 요청 정보
	 */
	public void asyncRegisterOrder(OrderRequest orderRequest) {
		// 주문 객체 생성
		Order order = OrderMapper.toOrder(orderRequest);

		// 주문 상태를 'pending' 으로 설정 후 저장
		orderRepository.save(order.pending());

		// 주문 상태 'pending' 이벤트 저장
		eventOutboxService.saveOrderReserveProductEvent(OrderReserveProductEvent.from(order));
	}

	/**
	 * 상품 예약이 완료된 후 주문을 완료 처리하는 메서드입니다.
	 *
	 * @param event 상품 예약 완료 이벤트
	 */
	public void complete(ProductReservedForOrderEvent event) {
		// 주문과 해당 주문의 상품 정보를 조회
		Order order = orderRepository.findByIdWithProducts(event.getOrderId())
			.orElseThrow(() -> new EntityNotFoundException("Order with ID " + event.getOrderId() + " not found."));

		// 주문 완료 처리
		order.complete();
	}

	/**
	 * 모든 주문 정보를 조회하여 응답 객체로 반환하는 메서드입니다.
	 *
	 * @return 모든 주문의 응답 리스트
	 */
	public List<OrderResponse> findAllOrders() {
		// 주문 리스트 조회 후 응답 객체로 변환
		return orderRepository.findAllWithProducts().stream()
			.map(OrderResponse::from)
			.toList();
	}

	/**
	 * 특정 주문 ID에 해당하는 주문 정보를 조회하여 응답 객체로 반환하는 메서드입니다.
	 *
	 * @param id 주문 ID
	 * @return 주문 응답 객체
	 */
	public OrderResponse findById(Long id) {
		// 주문 조회, 없으면 예외 발생
		return OrderResponse.from(
			orderRepository.findByIdWithProducts(id)
				.orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found.")));
	}

	/**
	 * 제품 예약 실패로 인해 주문을 취소하고 제품 실패 상태로 처리하는 메서드입니다.
	 *
	 * @param event 제품 예약 실패에 대한 세부 정보를 포함한 이벤트
	 */
	public void productFailed(ProductReservationFailedForOrderEvent event) {
		// 주문 조회, 없으면 예외 발생
		Order order = orderRepository.findById(event.getOrderId())
			.orElseThrow(() -> new EntityNotFoundException("Order not found"));
		// 제품 예약 실패 처리
		order.productFailed();
	}

}
