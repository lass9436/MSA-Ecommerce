package ecommerce.userService.messaging.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserApprovalFailedForOrderEvent {
	private Long orderId;
	private Long userSeq;

	public static UserApprovalFailedForOrderEvent from(OrderPendingEvent event) {
		return UserApprovalFailedForOrderEvent.builder()
			.orderId(event.getOrderId())
			.userSeq(event.getUserSeq())
			.build();
	}
}
