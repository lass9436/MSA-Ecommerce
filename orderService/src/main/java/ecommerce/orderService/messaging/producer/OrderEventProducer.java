package ecommerce.orderService.messaging.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ecommerce.orderService.messaging.event.OrderPendingEvent;
import ecommerce.orderService.messaging.event.OrderReserveProductEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

	private final KafkaTemplate<String, OrderReserveProductEvent> orderCreatedEventKafkaTemplate;
	private final KafkaTemplate<String, OrderPendingEvent> orderPendingEventKafkaTemplate;

	public void sendOrderPendingEvent(OrderPendingEvent event) {
		orderPendingEventKafkaTemplate.send("order-pending", event);
	}

	public void sendOrderReserveProductEvent(OrderReserveProductEvent event) {
		orderCreatedEventKafkaTemplate.send("order-reserve-product", event);
	}
}
