package ecommerce.orderService.order.controller;

import java.time.LocalDateTime;
import java.util.List;

import ecommerce.orderService.order.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponse {
	private Long orderId;
	private Long userSeq;
	private OrderStatus orderStatus;
	private LocalDateTime orderAt;
	private Long orderAmount;
	private List<OrderProductResponse> orderProductResponses;
}
