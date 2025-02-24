package ecommerce.productService.messaging.outbox;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import ecommerce.productService.messaging.event.publish.ProductReservationFailedForOrderEvent;
import ecommerce.productService.messaging.event.publish.ProductReservedForOrderEvent;
import ecommerce.productService.messaging.producer.ProductEventProducer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventOutboxListener {

	private final ProductEventProducer productEventProducer;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onProductReservedForOrderEvent(ProductReservedForOrderEvent event) {
		productEventProducer.sendProductReservedForOrderEvent(event);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onProductReservationFailedForOrderEvent(ProductReservationFailedForOrderEvent event) {
		productEventProducer.sendProductReservationFailedForOrderEvent(event);
	}
}
