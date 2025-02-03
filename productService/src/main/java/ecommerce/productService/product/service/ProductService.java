package ecommerce.productService.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.productService.exception.EntityNotFoundException;
import ecommerce.productService.messaging.event.OrderReserveProductEvent;
import ecommerce.productService.messaging.event.ProductReservedForOrderEvent;
import ecommerce.productService.messaging.event.ReservedProductItemEvent;
import ecommerce.productService.messaging.producer.ProductEventProducer;
import ecommerce.productService.product.dto.*;
import ecommerce.productService.product.domain.Product;
import ecommerce.productService.store.domain.Store;
import ecommerce.productService.product.repository.ProductRepository;
import ecommerce.productService.store.repository.StoreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;
	private final ProductEventProducer productEventProducer;

	/**
	 * 상품을 등록하는 메서드
	 *
	 * @param productRequest 상품 등록을 위한 요청 객체
	 * @return 등록된 상품 정보를 담은 ProductResponse 객체
	 * @throws EntityNotFoundException 스토어가 존재하지 않으면 예외 발생
	 */
	public ProductResponse registerProduct(ProductRequest productRequest) {
		// 스토어 ID로 스토어 조회, 없으면 예외 발생
		Store store = storeRepository.findById(productRequest.getStoreId())
			.orElseThrow(() -> new EntityNotFoundException("Store with ID " + productRequest.getStoreId() + " not found."));

		// 새 상품 객체 생성
		Product product = new Product(
			productRequest.getProductName(),
			productRequest.getProductCategory(),
			productRequest.getProductQuantity(),
			productRequest.getProductPrice()
		);

		// 상품을 스토어에 연결
		product.assignStore(store);

		// 상품 저장
		productRepository.save(product);

		return ProductResponse.from(product);
	}

	/**
	 * 모든 상품을 조회하는 메서드
	 *
	 * @return 모든 상품 정보를 담은 ProductResponse 객체 리스트
	 */
	public List<ProductResponse> findAllProducts() {
		// 모든 상품 조회 후 응답 객체로 변환
		return productRepository.findAll().stream()
			.map(ProductResponse::from)
			.toList();
	}

	/**
	 * 상품 ID로 상품 정보를 조회하는 메서드
	 *
	 * @param id 조회할 상품 ID
	 * @return 조회된 상품 정보를 담은 ProductResponse 객체
	 * @throws EntityNotFoundException 상품 ID가 없을 경우 예외 발생
	 */
	public ProductResponse findById(Long id) {
		// 상품 ID로 상품 조회, 없으면 예외 발생
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found."));
		return ProductResponse.from(product);
	}

	/**
	 * 상품 정보를 수정하는 메서드
	 *
	 * @param id 수정할 상품 ID
	 * @param productRequest 수정할 상품 정보
	 * @return 수정된 상품 정보를 담은 ProductResponse 객체
	 * @throws EntityNotFoundException 상품 ID가 없을 경우 예외 발생
	 */
	public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
		// 상품 ID로 상품 조회, 없으면 예외 발생
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found."));

		// 상품 정보 업데이트
		product.update(
			productRequest.getProductName(),
			productRequest.getProductCategory(),
			productRequest.getProductQuantity(),
			productRequest.getProductPrice()
		);

		return ProductResponse.from(product);
	}

	/**
	 * 상품을 삭제하는 메서드
	 *
	 * @param id 삭제할 상품 ID
	 * @throws EntityNotFoundException 상품 ID가 없을 경우 예외 발생
	 */
	public void deleteProduct(Long id) {
		// 상품 ID로 상품 조회, 없으면 예외 발생
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found."));

		// 상품 삭제
		productRepository.delete(product);
	}

	/**
	 * 상품 재고를 벌크로 증가시키는 메서드
	 *
	 * @param productBulkIncreaseRequest 재고 증가 요청 객체
	 * @throws EntityNotFoundException 상품 ID가 없을 경우 예외 발생
	 */
	public void bulkIncreaseProduct(@Valid ProductBulkIncreaseRequest productBulkIncreaseRequest) {
		// 요청된 상품 ID 리스트 추출
		List<Long> productIds = productBulkIncreaseRequest.getDetails().stream()
			.map(ProductBulkIncreaseRequestDetail::getProductId)
			.toList();

		// 상품 ID 리스트로 상품 조회 (락 사용)
		List<Product> products = productRepository.findByProductIdsWithLock(productIds);

		// 요청된 상품 개수와 조회된 상품 개수가 다르면 예외 발생
		if (products.size() != productIds.size()) {
			throw new EntityNotFoundException("One or more Product IDs not found.");
		}

		for (ProductBulkIncreaseRequestDetail detail : productBulkIncreaseRequest.getDetails()) {
			// 상품 ID에 해당하는 상품 찾기
			Product product = products.stream()
				.filter(p -> p.getProductId().equals(detail.getProductId()))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("Product with ID " + detail.getProductId() + " not found."));

			// 상품 재고 증가
			product.increaseStock(detail.getIncreaseQuantity());
		}
	}

	/**
	 * 요청된 상품 ID 리스트로 상품을 조회하는 메서드
	 *
	 * @param productBulkRequest 상품 ID 리스트 요청 객체
	 * @return 조회된 상품 정보를 담은 ProductResponse 객체 리스트
	 * @throws EntityNotFoundException 상품 ID가 없을 경우 예외 발생
	 */
	public List<ProductResponse> findAllById(@Valid ProductBulkRequest productBulkRequest) {
		// 요청에서 받은 상품 ID 리스트를 가져옴
		List<Product> products = productRepository.findAllById(productBulkRequest.getProductIds());

		// 조회된 상품 엔티티 리스트를 ProductResponse 로 변환하여 반환
		return products.stream()
			.map(ProductResponse::from)
			.toList();
	}

	/**
	 * 상품 재고를 벌크로 감소시키는 메서드
	 *
	 * @param productBulkDecreaseRequest 재고 감소 요청 객체
	 * @throws EntityNotFoundException 상품 ID가 없을 경우 예외 발생
	 */
	public void bulkDecreaseProduct(ProductBulkDecreaseRequest productBulkDecreaseRequest) {
		// 요청된 상품 ID 리스트 추출
		List<Long> productIds = productBulkDecreaseRequest.getDetails().stream()
			.map(ProductBulkDecreaseRequestDetail::getProductId)
			.toList();

		// 상품 ID 리스트로 상품 조회 (락 사용)
		List<Product> products = productRepository.findByProductIdsWithLock(productIds);

		// 요청된 상품 개수와 조회된 상품 개수가 다르면 예외 발생
		if (products.size() != productIds.size()) {
			throw new EntityNotFoundException("One or more Product IDs not found.");
		}

		for (ProductBulkDecreaseRequestDetail detail : productBulkDecreaseRequest.getDetails()) {
			// 상품 ID에 해당하는 상품 찾기
			Product product = products.stream()
				.filter(p -> p.getProductId().equals(detail.getProductId()))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("Product with ID " + detail.getProductId() + " not found."));

			// 상품 재고 감소
			product.decreaseStock(detail.getDecreaseQuantity());
		}
	}

	/**
	 * 상품을 예약하는 메서드
	 *
	 * @param event 주문 예약 상품 이벤트 객체
	 * @throws EntityNotFoundException 스토어나 상품이 존재하지 않으면 예외 발생
	 * @throws IllegalStateException 총 주문 금액과 상품 금액 불일치시 예외 발생
	 */
	public void reserveProduct(OrderReserveProductEvent event) {
		long totalReservedAmount = 0L;

		for (ReservedProductItemEvent productEvent : event.getProducts()) {
			// 스토어 ID로 스토어 조회, 없으면 예외 발생
			Store store = storeRepository.findById(productEvent.getStoreId())
				.orElseThrow(() -> new EntityNotFoundException("Store not found: " + productEvent.getStoreId()));

			// 상품 ID로 상품 조회, 없으면 예외 발생
			Product product = productRepository.findById(productEvent.getProductId())
				.orElseThrow(() -> new EntityNotFoundException("Product not found: " + productEvent.getProductId()));

			// 상품이 올바른 스토어에 속하는지 검증
			product.validateStore(store);

			// 상품 가격이 주문된 가격과 일치하는지 검증
			product.validatePrice(productEvent.getProductPrice());

			// 상품 재고 감소 (주문 수량만큼)
			product.decreaseStock(productEvent.getOrderQuantity());

			// 총 예약 금액 계산
			totalReservedAmount += productEvent.getProductPrice() * productEvent.getOrderQuantity();
		}

		// 주문 총액과 상품 총액이 일치하는지 검증
		if (!event.getOrderAmount().equals(totalReservedAmount)) {
			throw new IllegalStateException("총 주문 금액과 상품 총액 불일치: " + totalReservedAmount + " vs " + event.getOrderAmount());
		}

		// 상품 예약 완료 이벤트 전송
		productEventProducer.sendProductReservedForOrderEvent(ProductReservedForOrderEvent.from(event.getOrderId()));
	}
}
