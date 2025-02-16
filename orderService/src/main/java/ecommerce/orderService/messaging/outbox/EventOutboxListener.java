package ecommerce.orderService.messaging.outbox;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import ecommerce.orderService.messaging.event.OrderPendingEvent;
import ecommerce.orderService.messaging.event.OrderReserveProductEvent;
import ecommerce.orderService.messaging.producer.OrderEventProducer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventOutboxListener {

	private final OrderEventProducer orderEventProducer;

	@EventListener
	public void onOrderPendingEvent(OrderPendingEvent event) {
		orderEventProducer.sendOrderPendingEvent(event);
	}

	@EventListener
	public void onOrderReserveProductEvent(OrderReserveProductEvent event) {
		orderEventProducer.sendOrderReserveProductEvent(event);
	}
}
