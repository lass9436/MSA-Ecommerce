package ecommerce.orderService.messaging.event;

import java.util.List;

import ecommerce.orderService.order.domain.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderReserveProductEvent {
	private Long orderId;
	private Long orderAmount;
	private List<ReservedProductItemEvent> products;

	public static OrderReserveProductEvent from(Order order) {
		return OrderReserveProductEvent.builder()
			.orderId(order.getOrderId())
			.orderAmount(order.getOrderAmount())
			.products(order.getOrderProducts().stream()
				.map(ReservedProductItemEvent::from)
				.toList())
			.build();
	}
}
