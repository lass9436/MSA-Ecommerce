package ecommerce.userService.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.userService.messaging.event.OrderPendingEvent;
import ecommerce.userService.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

	private final UserService userService;
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = "order-pending")
	public void handleOrderPending(byte[] message) {
		try {
			OrderPendingEvent orderPendingEvent = objectMapper.readValue(message, OrderPendingEvent.class);
			userService.validateUserForOrder(orderPendingEvent);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
