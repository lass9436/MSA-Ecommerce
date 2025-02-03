package ecommerce.orderService.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import ecommerce.orderService.order.domain.Order;
import ecommerce.orderService.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponse {
	private Long orderId;
	private Long userSeq;
	private OrderStatus orderStatus;
	private LocalDateTime orderAt;
	private Long orderAmount;
	private List<OrderProductResponse> orderProductResponses;

	public static OrderResponse from(Order order) {
		return OrderResponse.builder()
			.orderId(order.getOrderId())
			.userSeq(order.getUserSeq())
			.orderStatus(order.getOrderStatus())
			.orderAt(order.getOrderAt())
			.orderAmount(order.getOrderAmount())
			.orderProductResponses(
				order.getOrderProducts().stream()
					.map(OrderProductResponse::from)
					.toList()
			)
			.build();
	}
}
