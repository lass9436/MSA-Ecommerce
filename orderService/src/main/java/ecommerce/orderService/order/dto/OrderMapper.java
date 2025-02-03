package ecommerce.orderService.order.dto;

import java.time.LocalDateTime;

import ecommerce.orderService.order.domain.Order;
import ecommerce.orderService.order.domain.OrderProduct;

public class OrderMapper {

	public static Order toOrder(OrderRequest orderRequest) {
		Order order = new Order(
			orderRequest.getUserSeq(),
			orderRequest.getOrderAmount(),
			orderRequest.getOrderStatus(),
			LocalDateTime.now()
		);

		orderRequest.getOrderProductRequests().forEach(orderProductRequest -> {
			new OrderProduct(
				order,
				orderProductRequest.getStoreId(),
				orderProductRequest.getProductId(),
				orderProductRequest.getOrderQuantity(),
				orderProductRequest.getProductPrice()
			);
		});

		return order;
	}
}
