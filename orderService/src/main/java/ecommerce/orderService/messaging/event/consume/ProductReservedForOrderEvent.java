package ecommerce.orderService.messaging.event.consume;

import ecommerce.orderService.messaging.event.ConsumeEvent;
import lombok.Getter;

@Getter
public class ProductReservedForOrderEvent extends ConsumeEvent {
	private Long orderId;
}
