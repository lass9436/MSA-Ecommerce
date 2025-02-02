package ecommerce.productService.messaging.consumer;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.productService.messaging.event.OrderReserveProductEvent;
import ecommerce.productService.product.service.ProductService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

	private final ProductService productService;
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = "order-reserve-product")
	public void handleOrderReserveProduct(byte[] message) {
		try {
			OrderReserveProductEvent event = objectMapper.readValue(message, OrderReserveProductEvent.class);
			productService.reserveProduct(event);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
