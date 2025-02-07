package ecommerce.userService.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.userService.messaging.event.OrderPendingEvent;
import ecommerce.userService.messaging.event.UserApprovalFailedForOrderEvent;
import ecommerce.userService.messaging.producer.UserEventProducer;
import ecommerce.userService.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

	private final UserService userService;
	private final ObjectMapper objectMapper;
	private final UserEventProducer userEventProducer;

	/**
	 * Kafka 메시지 리스너 메서드로, "order-pending" 토픽에서 전송된 메시지를 처리합니다.
	 *
	 * 1. 수신된 메시지를 `OrderPendingEvent` 객체로 변환합니다.
	 * 2. 변환된 이벤트를 기반으로 사용자 승인 검증 작업을 수행합니다.
	 * 3. 사용자 승인 검증에 실패할 경우, 보상 이벤트(`UserApprovalFailedForOrderEvent`)를 생성하여 전송합니다.
	 *    이를 통해 비즈니스 프로세스의 보상 로직을 처리할 수 있도록 합니다.
	 */
	@KafkaListener(topics = "order-pending")
	public void handleOrderPending(byte[] message) {
		try {
			// 수신된 메시지를 OrderPendingEvent 객체로 변환
			OrderPendingEvent orderPendingEvent = objectMapper.readValue(message, OrderPendingEvent.class);

			try {
				// 변환된 이벤트를 기반으로 사용자 승인 검증 수행
				userService.validateUserForOrder(orderPendingEvent);
			} catch (Exception e) {
				// 사용자 승인 검증 실패 시, 보상 이벤트를 생성
				UserApprovalFailedForOrderEvent compensatingEvent = UserApprovalFailedForOrderEvent.from(orderPendingEvent);
				// 보상 이벤트를 이벤트 프로듀서를 통해 전송
				userEventProducer.sendUserApprovalFailedForOrderEvent(compensatingEvent);
			}
		} catch (Exception e) {
			// 메시지 파싱 또는 처리 중 발생한 예외를 출력
			System.out.println(e.getMessage());
		}
	}
}
