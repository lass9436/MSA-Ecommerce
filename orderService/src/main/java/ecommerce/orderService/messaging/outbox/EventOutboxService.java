package ecommerce.orderService.messaging.outbox;

import static ecommerce.orderService.messaging.event.EventName.*;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.orderService.messaging.event.publish.OrderPendingEvent;
import ecommerce.orderService.messaging.event.publish.OrderReserveProductEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EventOutboxService {

	private final ObjectMapper objectMapper;
	private final EventOutboxRepository eventOutboxRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	public void saveOrderPendingEvent(OrderPendingEvent event) {
		saveEvent(event, ORDER_PENDING, event.getIdempotencyKey());
	}

	public void saveOrderReserveProductEvent(OrderReserveProductEvent event) {
		saveEvent(event, ORDER_RESERVE_PRODUCT, event.getIdempotencyKey());
	}

	private void saveEvent(Object event, String eventName, String idempotencyKey) {
		String payload = objectMapper.convertValue(event, String.class);
		EventOutbox eventOutbox = createEventOutbox(payload, eventName, idempotencyKey);
		eventOutboxRepository.save(eventOutbox);
		applicationEventPublisher.publishEvent(event);
	}

	private EventOutbox createEventOutbox(String payload, String eventName, String idempotencyKey) {
		return EventOutbox.builder()
			.idempotencyKey(idempotencyKey)
			.eventName(eventName)
			.type(EventType.PUBLISH)
			.status(EventStatus.PENDING)
			.payload(payload)
			.createdAt(LocalDateTime.now())
			.build();
	}

	public void successEvent(String idempotencyKey) {
		EventOutbox eventOutbox = eventOutboxRepository.findByIdempotencyKey(idempotencyKey)
			.orElseThrow(() -> new IllegalArgumentException("Event not found for idempotency key: " + idempotencyKey));
		eventOutbox.success();
	}

	public void failureEvent(String idempotencyKey) {
		EventOutbox eventOutbox = eventOutboxRepository.findByIdempotencyKey(idempotencyKey)
			.orElseThrow(() -> new IllegalArgumentException("Event not found for idempotency key: " + idempotencyKey));
		eventOutbox.failure();
	}
}
