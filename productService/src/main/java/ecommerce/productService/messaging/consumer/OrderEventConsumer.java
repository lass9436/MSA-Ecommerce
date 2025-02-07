package ecommerce.productService.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.productService.messaging.event.OrderReserveProductEvent;
import ecommerce.productService.messaging.event.ProductReservationFailedForOrderEvent;
import ecommerce.productService.messaging.producer.ProductEventProducer;
import ecommerce.productService.product.service.ProductService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

	private final ProductService productService;
	private final ObjectMapper objectMapper;
	private final ProductEventProducer productEventProducer;

	/**
	 * Kafka 메시지 리스너 메서드로, "order-reserve-product" 토픽에서 전송된 메시지를 처리합니다.
	 *
	 * 1. 메시지를 수신한 후 `OrderReserveProductEvent` 객체로 변환합니다.
	 * 2. ProductService 를 이용해 제품 예약 처리를 수행합니다.
	 * 3. 만약 제품 예약 처리 중 실패가 발생할 경우, 실패 이벤트를 `ProductEventProducer`를 통해 전송합니다.
	 */
	@KafkaListener(topics = "order-reserve-product")
	public void handleOrderReserveProduct(byte[] message) {
		try {
			// 수신된 메시지를 OrderReserveProductEvent 객체로 변환
			OrderReserveProductEvent event = objectMapper.readValue(message, OrderReserveProductEvent.class);

			try {
				// 변환된 이벤트 객체를 사용하여 제품 예약 처리
				productService.reserveProduct(event);
			} catch (Exception e) {
				// 예약 처리 실패 시, 실패 이벤트 객체를 생성
				ProductReservationFailedForOrderEvent failedEvent = ProductReservationFailedForOrderEvent.from(event);
				// 실패 이벤트를 프로듀서를 통해 전송
				productEventProducer.sendProductReservationFailedForOrderEvent(failedEvent);
			}
		} catch (Exception e) {
			// 메시지 파싱 또는 처리 중 발생한 예외를 출력
			System.out.println(e.getMessage());
		}
	}
}
