package ecommerce.productService.messaging.outbox;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.productService.messaging.event.ConsumeEvent;
import ecommerce.productService.messaging.event.PublishEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EventOutboxService {

	private final ObjectMapper objectMapper;
	private final EventOutboxRepository eventOutboxRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	public <T extends PublishEvent> void savePublishEventAndPublish(T event, String eventName) {
		String payload = objectMapper.convertValue(event, String.class);
		EventOutbox eventOutbox = createEventOutbox(payload, EventType.PUBLISH, eventName, event.getIdempotencyKey());
		eventOutboxRepository.save(eventOutbox);
		applicationEventPublisher.publishEvent(event);
	}

	public <T extends ConsumeEvent> void saveConsumeEvent(T event, String eventName, String idempotencyKey) {
		if (isEventExists(idempotencyKey)) {
			return;
		}
		String payload = objectMapper.convertValue(event, String.class);
		EventOutbox eventOutbox = createEventOutbox(payload, EventType.CONSUME, eventName, idempotencyKey);
		eventOutboxRepository.save(eventOutbox);
	}

	private boolean isEventExists(String idempotencyKey) {
		return eventOutboxRepository.findByIdempotencyKey(idempotencyKey)
			.map(existingEvent -> {
				if (existingEvent.getStatus() != EventStatus.PENDING) {
					throw new IllegalStateException("이벤트가 이미 처리되었습니다: " + idempotencyKey);
				}
				return true;
			})
			.orElse(false);
	}

	private EventOutbox createEventOutbox(String payload, EventType type, String eventName, String idempotencyKey) {
		return EventOutbox.builder()
			.idempotencyKey(idempotencyKey)
			.eventName(eventName)
			.type(type)
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
