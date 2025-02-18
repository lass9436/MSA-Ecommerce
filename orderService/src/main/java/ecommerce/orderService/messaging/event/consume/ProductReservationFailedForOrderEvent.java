package ecommerce.orderService.messaging.event.consume;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductReservationFailedForOrderEvent {
	private Long orderId;
}
