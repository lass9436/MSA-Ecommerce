package ecommerce.orderService.messaging.event.publish;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import ecommerce.orderService.order.domain.Order;
import lombok.Getter;

@Getter
public class OrderPendingEvent extends ApplicationEvent {

	private final String idempotencyKey;
	private final Long orderId;
	private final Long userSeq;
	private final Long orderAmount;

	private OrderPendingEvent(Order order) {
		super(order); // ApplicationEvent 의 생성자에 source 객체를 전달
		this.idempotencyKey = UUID.randomUUID().toString();
		this.orderId = order.getOrderId();
		this.userSeq = order.getUserSeq();
		this.orderAmount = order.getOrderAmount();
	}

	public static OrderPendingEvent from(Order order) {
		return new OrderPendingEvent(order);
	}
}
