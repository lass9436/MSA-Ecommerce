package ecommerce.productService.messaging.producer;

import static ecommerce.productService.messaging.event.EventName.*;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import ecommerce.productService.messaging.event.produce.ProductReservationFailedForOrderEvent;
import ecommerce.productService.messaging.event.produce.ProductReservedForOrderEvent;
import ecommerce.productService.messaging.outbox.EventOutboxService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventProducer {

	private final KafkaTemplate<String, ProductReservedForOrderEvent> productReservedForOrderEventKafkaTemplate;
	private final KafkaTemplate<String, ProductReservationFailedForOrderEvent> productReservationFailedForOrderEventKafkaTemplate;
	private final EventOutboxService eventOutboxService;

	public void sendProductReservedForOrderEvent(ProductReservedForOrderEvent event) {
		productReservedForOrderEventKafkaTemplate.send(PRODUCT_RESERVED_FOR_ORDER, event)
			.whenComplete((recordMetadata, exception) ->
				handleEventCompletion(event.getIdempotencyKey(), recordMetadata, exception)
			);
	}

	public void sendProductReservationFailedForOrderEvent(ProductReservationFailedForOrderEvent event) {
		productReservationFailedForOrderEventKafkaTemplate.send(PRODUCT_RESERVATION_FAILED_FOR_ORDER, event)
			.whenComplete((recordMetadata, exception) ->
				handleEventCompletion(event.getIdempotencyKey(), recordMetadata, exception)
			);
	}

	private void handleEventCompletion(String idempotencyKey, SendResult<String, ?> recordMetadata,
		Throwable exception) {
		if (recordMetadata != null) {
			eventOutboxService.successEvent(idempotencyKey);
		}
		if (exception != null) {
			eventOutboxService.failureEvent(idempotencyKey);
		}
	}

}
