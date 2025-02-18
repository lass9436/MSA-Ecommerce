package ecommerce.orderService.messaging.event.consume;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserApprovalFailedForOrderEvent {
	private Long orderId;
	private Long userSeq;
}
