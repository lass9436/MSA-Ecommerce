package ecommerce.orderService.messaging.event.consume;

import lombok.Getter;

@Getter
public class ProductReservedForOrderEvent {
	private Long orderId;
}
