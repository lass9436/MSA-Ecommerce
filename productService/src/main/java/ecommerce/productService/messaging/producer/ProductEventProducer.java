package ecommerce.productService.messaging.producer;

import static ecommerce.productService.messaging.event.EventName.*;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ecommerce.productService.messaging.event.produce.ProductReservationFailedForOrderEvent;
import ecommerce.productService.messaging.event.produce.ProductReservedForOrderEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventProducer {

	private final KafkaTemplate<String, ProductReservedForOrderEvent> productReservedForOrderEventKafkaTemplate;
	private final KafkaTemplate<String, ProductReservationFailedForOrderEvent> productReservationFailedForOrderEventKafkaTemplate;

	public void sendProductReservedForOrderEvent(ProductReservedForOrderEvent event) {
		productReservedForOrderEventKafkaTemplate.send(PRODUCT_RESERVED_FOR_ORDER, event);
	}

	public void sendProductReservationFailedForOrderEvent(ProductReservationFailedForOrderEvent event) {
		productReservationFailedForOrderEventKafkaTemplate.send(PRODUCT_RESERVATION_FAILED_FOR_ORDER, event);
	}

}
