package ecommerce.orderService.messaging.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductReservationFailedForOrderEvent {
	private Long orderId;
}
