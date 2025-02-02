package ecommerce.orderService.messaging.event;

import ecommerce.orderService.order.domain.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderPendingEvent {

	private Long orderId;
	private Long userSeq;
	private Long orderAmount;

	public static OrderPendingEvent from(Order order) {
		return OrderPendingEvent.builder()
			.orderId(order.getOrderId())
			.userSeq(order.getUserSeq())
			.orderAmount(order.getOrderAmount())
			.build();
	}
}
