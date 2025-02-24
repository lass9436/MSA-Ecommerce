package ecommerce.orderService.messaging.event.facade;

import static ecommerce.orderService.messaging.event.EventName.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.orderService.messaging.event.produce.OrderReserveProductEvent;
import ecommerce.orderService.messaging.outbox.EventOutboxService;
import ecommerce.orderService.order.dto.OrderRequest;
import ecommerce.orderService.order.dto.OrderResponse;
import ecommerce.orderService.order.service.OrderService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class OrderEventFacade {

	private final OrderService orderService;
	private final EventOutboxService eventOutboxService;

	public void asyncRegisterOrder(OrderRequest orderRequest) {
		OrderResponse orderResponse = orderService.asyncRegisterOrder(orderRequest);
		eventOutboxService.saveProduceEventAndPublish(OrderReserveProductEvent.from(orderResponse),
			ORDER_RESERVE_PRODUCT);
	}
}
