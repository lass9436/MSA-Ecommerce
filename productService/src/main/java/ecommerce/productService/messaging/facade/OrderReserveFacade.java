package ecommerce.productService.messaging.facade;

import static ecommerce.productService.messaging.event.EventName.*;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.productService.messaging.event.consume.OrderReserveProductEvent;
import ecommerce.productService.messaging.event.produce.ProductReservationFailedForOrderEvent;
import ecommerce.productService.messaging.event.produce.ProductReservedForOrderEvent;
import ecommerce.productService.messaging.outbox.EventOutboxService;
import ecommerce.productService.product.service.ProductService;
import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class OrderReserveFacade {

	private final ProductService productService;
	private final EventOutboxService eventOutboxService;

	public void saveEventAndCommit(OrderReserveProductEvent event, Acknowledgment ack) {
		eventOutboxService.saveConsumeEvent(event, ORDER_RESERVE_PRODUCT, event.getIdempotencyKey());
		ack.acknowledge();
	}

	public void processOrderReserve(OrderReserveProductEvent event) {
		productService.reserveProduct(event);
		eventOutboxService.successEvent(event.getIdempotencyKey());
		eventOutboxService.saveProduceEventAndPublish(ProductReservedForOrderEvent.from(event),
			PRODUCT_RESERVED_FOR_ORDER);
	}

	public void compensateOrderReserve(OrderReserveProductEvent event) {
		eventOutboxService.failureEvent(event.getIdempotencyKey());
		eventOutboxService.saveProduceEventAndPublish(ProductReservationFailedForOrderEvent.from(event),
			PRODUCT_RESERVATION_FAILED_FOR_ORDER);
	}
}
