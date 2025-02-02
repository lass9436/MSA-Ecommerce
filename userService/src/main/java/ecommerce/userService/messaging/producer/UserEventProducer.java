package ecommerce.userService.messaging.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ecommerce.userService.messaging.event.UserApprovedForOrderEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

	private final KafkaTemplate<String, UserApprovedForOrderEvent> userApprovedForOrderKafkaTemplate;

	public void sendUserApprovedForOrderEvent(UserApprovedForOrderEvent event) {
		userApprovedForOrderKafkaTemplate.send("user-approved-for-order", event);
	}
}
