package ecommerce.orderService.messaging.consumer;

import static ecommerce.orderService.messaging.event.EventName.*;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.orderService.messaging.event.UserApprovalFailedForOrderEvent;
import ecommerce.orderService.messaging.event.UserApprovedForOrderEvent;
import ecommerce.orderService.order.service.OrderService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventConsumer {

	private final OrderService orderService;
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = USER_APPROVED_FOR_ORDER)
	public void handleUserApprovedForOrder(byte[] message) {
		try {
			UserApprovedForOrderEvent event = objectMapper.readValue(message, UserApprovedForOrderEvent.class);
			orderService.reserveProduct(event);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@KafkaListener(topics = USER_APPROVAL_FAILED_FOR_ORDER)
	public void handleUserApprovalFailedForOrder(byte[] message) {
		try {
			UserApprovalFailedForOrderEvent event = objectMapper.readValue(message, UserApprovalFailedForOrderEvent.class);
			orderService.userFailed(event);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
