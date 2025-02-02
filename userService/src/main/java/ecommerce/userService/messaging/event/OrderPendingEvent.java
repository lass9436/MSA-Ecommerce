package ecommerce.userService.messaging.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderPendingEvent {

	private Long orderId;
	private Long userSeq;
	private Long orderAmount;
}
