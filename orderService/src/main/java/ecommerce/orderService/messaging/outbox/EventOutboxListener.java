package ecommerce.orderService.messaging.outbox;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import ecommerce.orderService.messaging.event.OrderPendingEvent;
import ecommerce.orderService.messaging.event.OrderReserveProductEvent;
import ecommerce.orderService.messaging.producer.OrderEventProducer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventOutboxListener {

	private final OrderEventProducer orderEventProducer;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onOrderPendingEvent(OrderPendingEvent event) {
		orderEventProducer.sendOrderPendingEvent(event);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onOrderReserveProductEvent(OrderReserveProductEvent event) {
		orderEventProducer.sendOrderReserveProductEvent(event);
	}
}
