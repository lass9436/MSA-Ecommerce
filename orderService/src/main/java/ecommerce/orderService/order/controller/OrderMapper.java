package ecommerce.orderService.order.controller;

import java.time.LocalDateTime;
import java.util.List;

import ecommerce.orderService.order.domain.Order;
import ecommerce.orderService.order.domain.OrderProduct;

public class OrderMapper {

	private OrderMapper() {
	}

	public static Order toOrder(OrderRequest orderRequest) {
		Order order = new Order(
			orderRequest.getUserSeq(),
			orderRequest.getOrderAmount(),
			orderRequest.getOrderStatus(),
			LocalDateTime.now()
		);

		orderRequest.getOrderProductRequests().forEach(request -> toOrderProduct(request, order));
		return order;
	}

	private static void toOrderProduct(OrderProductRequest request, Order order) {
		new OrderProduct(
			order,
			request.getStoreId(),
			request.getProductId(),
			request.getOrderQuantity(),
			request.getProductPrice()
		);
	}

	public static OrderResponse toOrderResponse(Order order) {
		List<OrderProductResponse> orderProductResponses = order.getOrderProducts().stream()
			.map(OrderMapper::toOrderProductResponse)
			.toList();

		return new OrderResponse(
			order.getOrderId(),
			order.getUserSeq(),
			order.getOrderStatus(),
			order.getOrderAt(),
			order.getOrderAmount(),
			orderProductResponses
		);
	}

	private static OrderProductResponse toOrderProductResponse(OrderProduct orderProduct) {
		OrderProductResponse response = new OrderProductResponse();
		response.setOrderProductId(orderProduct.getOrderProductId());
		response.setStoreId(orderProduct.getStoreId());
		response.setProductId(orderProduct.getProductId());
		response.setOrderQuantity(orderProduct.getOrderQuantity());
		response.setProductPrice(orderProduct.getProductPrice());
		return response;
	}
}
