package ecommerce.orderService.messaging.event.consume;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserApprovedForOrderEvent {
	private Long orderId;
	private Long userSeq;
}
