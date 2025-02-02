package ecommerce.productService.messaging.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ecommerce.productService.messaging.event.ProductReservedForOrderEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventProducer {

	private final KafkaTemplate<String, ProductReservedForOrderEvent> productReservedForOrderEventKafkaTemplate;

	public void sendProductReservedForOrderEvent(ProductReservedForOrderEvent event) {
		productReservedForOrderEventKafkaTemplate.send("product-reserved-for-order", event);
	}
}
