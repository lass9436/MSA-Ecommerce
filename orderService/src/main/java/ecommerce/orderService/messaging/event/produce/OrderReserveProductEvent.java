package ecommerce.orderService.messaging.event.produce;

import java.util.List;

import ecommerce.orderService.messaging.event.ProduceEvent;
import ecommerce.orderService.order.dto.OrderResponse;
import lombok.Getter;

@Getter
public class OrderReserveProductEvent extends ProduceEvent {
	private final Long orderId;
	private final Long orderAmount;
	private final List<ReservedProductItemEvent> products;

	private OrderReserveProductEvent(OrderResponse order) {
		super(order); // ApplicationEvent 의 source 객체 전달
		this.orderId = order.getOrderId();
		this.orderAmount = order.getOrderAmount();
		this.products = order.getOrderProductResponses().stream()
			.map(ReservedProductItemEvent::from)
			.toList();
	}

	public static OrderReserveProductEvent from(OrderResponse order) {
		return new OrderReserveProductEvent(order);
	}
}
