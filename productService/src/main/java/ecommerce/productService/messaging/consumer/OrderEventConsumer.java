package ecommerce.productService.messaging.consumer;

import static ecommerce.productService.messaging.event.EventName.*;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.productService.messaging.event.consume.OrderReserveProductEvent;
import ecommerce.productService.messaging.facade.OrderReserveFacade;
import lombok.RequiredArgsConstructor;

/**
 * 주문 서비스로부터 발행된 이벤트를 수신하고 처리하는 Kafka 컨슈머 클래스입니다.
 * 주문 예약 관련 이벤트를 처리하고, 실패 시 보상 트랜잭션을 실행합니다.
 * 주요 기능:
 * - Kafka 메시지 수신 및 역직렬화
 * - 이벤트 영속성 보장
 * - 멱등성 처리
 * - 예외 상황 처리 및 보상 트랜잭션
 */
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

	private final ObjectMapper objectMapper;
	private final OrderReserveFacade orderReserveFacade;

	/**
	 * ORDER_RESERVE_PRODUCT 토픽의 메시지를 처리하는 Kafka 리스너 메서드입니다.
	 * 처리 과정:
	 * 1. 메시지를 OrderReserveProductEvent 객체로 변환
	 * 2. 이벤트를 저장하고 Kafka 메시지 승인
	 * 3. 주문 예약 처리 실행
	 * 4. 실패 시 보상 트랜잭션 실행
	 *
	 * @param message Kafka 로부터 수신한 바이트 배열 메시지
	 * @param ack Kafka 메시지 승인을 위한 Acknowledgment 객체
	 */
	@KafkaListener(topics = ORDER_RESERVE_PRODUCT)
	public void handleOrderReserveProduct(byte[] message, Acknowledgment ack) {
		try {
			// 수신된 메시지를 이벤트 객체로 변환
			OrderReserveProductEvent event = objectMapper.readValue(message, OrderReserveProductEvent.class);

			// 이벤트 저장 및 메시지 승인
			orderReserveFacade.saveEventAndCommit(event, ack);

			try {
				// 주문 예약 프로세스 실행
				orderReserveFacade.processOrderReserve(event);
			} catch (Exception e) {
				// 실패 시 보상 트랜잭션 실행
				orderReserveFacade.compensateOrderReserve(event);
			}
		} catch (Exception e) {
			// 메시지 파싱 실패 혹은 메시지 저장 실패
			System.out.println(e.getMessage());
		}
	}
}
