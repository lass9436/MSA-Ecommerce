package ecommerce.orderService.messaging.event;

import lombok.Getter;

@Getter
public class ProductReservedForOrderEvent {
	private Long orderId;
}
