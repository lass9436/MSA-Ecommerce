package ecommerce.orderService.messaging.event;

import org.springframework.context.ApplicationEvent;

import ecommerce.orderService.order.domain.Order;
import lombok.Getter;

@Getter
public class OrderPendingEvent extends ApplicationEvent {

	private Long orderId;
	private Long userSeq;
	private Long orderAmount;

	private OrderPendingEvent(Order order) {
		super(order); // ApplicationEvent의 생성자에 source 객체를 전달
		this.orderId = order.getOrderId();
		this.userSeq = order.getUserSeq();
		this.orderAmount = order.getOrderAmount();
	}

	public static OrderPendingEvent from(Order order) {
		return new OrderPendingEvent(order);
	}
}
