package ecommerce.orderService.messaging.producer;

import static ecommerce.orderService.messaging.event.EventName.*;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import ecommerce.orderService.messaging.event.OrderPendingEvent;
import ecommerce.orderService.messaging.event.OrderReserveProductEvent;
import ecommerce.orderService.messaging.outbox.EventOutboxService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

	private final KafkaTemplate<String, OrderReserveProductEvent> orderCreatedEventKafkaTemplate;
	private final KafkaTemplate<String, OrderPendingEvent> orderPendingEventKafkaTemplate;
	private final EventOutboxService eventOutboxService;

	public void sendOrderPendingEvent(OrderPendingEvent event) {
		orderPendingEventKafkaTemplate.send(ORDER_PENDING, event)
			.whenComplete((recordMetadata, exception) -> {
				handleEventCompletion(event.getIdempotencyKey(), recordMetadata, exception);
			});
	}

	public void sendOrderReserveProductEvent(OrderReserveProductEvent event) {
		orderCreatedEventKafkaTemplate.send(ORDER_RESERVE_PRODUCT, event)
			.whenComplete((recordMetadata, exception) -> {
				handleEventCompletion(event.getIdempotencyKey(), recordMetadata, exception);
			});
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
