package ecommerce.orderService.messaging.outbox;

import static ecommerce.orderService.messaging.event.EventName.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.orderService.messaging.event.OrderPendingEvent;
import ecommerce.orderService.messaging.event.OrderReserveProductEvent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventOutboxService {

	private final ObjectMapper objectMapper;
	private final EventOutboxRepository eventOutboxRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	public void saveOrderPendingEvent(OrderPendingEvent event) {
		saveEvent(event, ORDER_PENDING);
	}

	public void saveOrderReserveProductEvent(OrderReserveProductEvent event) {
		saveEvent(event, ORDER_RESERVE_PRODUCT);
	}

	private void saveEvent(Object event, String eventName) {
		String payload = objectMapper.convertValue(event, String.class);
		EventOutbox eventOutbox = createEventOutbox(payload, eventName);
		eventOutboxRepository.save(eventOutbox);
		applicationEventPublisher.publishEvent(event);
	}

	private EventOutbox createEventOutbox(String payload, String eventName) {
		return EventOutbox.builder()
			.idempotencyKey(UUID.randomUUID().toString())
			.eventName(eventName)
			.type(EventType.PUBLISH)
			.status(EventStatus.PENDING)
			.payload(payload)
			.createdAt(LocalDateTime.now())
			.build();
	}

}
