package ecommerce.orderService.messaging.event.consume;

import ecommerce.orderService.messaging.event.ConsumeEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductReservationFailedForOrderEvent extends ConsumeEvent {
	private Long orderId;
}
