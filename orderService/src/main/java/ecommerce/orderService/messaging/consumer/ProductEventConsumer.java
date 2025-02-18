package ecommerce.orderService.messaging.consumer;

import static ecommerce.orderService.messaging.event.EventName.*;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.orderService.messaging.event.consume.ProductReservationFailedForOrderEvent;
import ecommerce.orderService.messaging.event.consume.ProductReservedForOrderEvent;
import ecommerce.orderService.order.service.OrderService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventConsumer {

	private final OrderService orderService;
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = PRODUCT_RESERVED_FOR_ORDER)
	public void handleProductReservedForOrder(byte[] message) {

		try {
			ProductReservedForOrderEvent event = objectMapper.readValue(message, ProductReservedForOrderEvent.class);
			orderService.complete(event);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@KafkaListener(topics = PRODUCT_RESERVATION_FAILED_FOR_ORDER)
	public void handleProductReservationFailedForOrder(byte[] message) {
		try {
			ProductReservationFailedForOrderEvent event = objectMapper.readValue(message, ProductReservationFailedForOrderEvent.class);
			orderService.productFailed(event);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
