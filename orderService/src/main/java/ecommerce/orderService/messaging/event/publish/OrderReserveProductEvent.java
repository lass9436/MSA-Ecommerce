package ecommerce.orderService.messaging.event.publish;

import java.util.List;
import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import ecommerce.orderService.order.domain.Order;
import lombok.Getter;

@Getter
public class OrderReserveProductEvent extends ApplicationEvent {
	private final String idempotencyKey;
	private final Long orderId;
	private final Long orderAmount;
	private final List<ReservedProductItemEvent> products;

	private OrderReserveProductEvent(Order order) {
		super(order); // ApplicationEvent 의 source 객체 전달
		this.idempotencyKey = UUID.randomUUID().toString();
		this.orderId = order.getOrderId();
		this.orderAmount = order.getOrderAmount();
		this.products = order.getOrderProducts().stream()
			.map(ReservedProductItemEvent::from)
			.toList();
	}

	public static OrderReserveProductEvent from(Order order) {
		return new OrderReserveProductEvent(order);
	}
}
