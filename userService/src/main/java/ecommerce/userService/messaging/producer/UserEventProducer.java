package ecommerce.userService.messaging.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ecommerce.userService.messaging.event.UserApprovalFailedForOrderEvent;
import ecommerce.userService.messaging.event.UserApprovedForOrderEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

	private final KafkaTemplate<String, UserApprovedForOrderEvent> userApprovedForOrderKafkaTemplate;
	private final KafkaTemplate<String, UserApprovalFailedForOrderEvent> userApprovalFailedForOrderKafkaTemplate;

	public void sendUserApprovedForOrderEvent(UserApprovedForOrderEvent event) {
		userApprovedForOrderKafkaTemplate.send("user-approved-for-order", event);
	}

	public void sendUserApprovalFailedForOrderEvent(UserApprovalFailedForOrderEvent event) {
		userApprovalFailedForOrderKafkaTemplate.send("user-approval-failed-for-order", event);
	}
}
