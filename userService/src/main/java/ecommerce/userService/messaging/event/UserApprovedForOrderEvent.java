package ecommerce.userService.messaging.event;

import ecommerce.userService.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserApprovedForOrderEvent {
	private Long orderId;
	private Long userSeq;

	public static UserApprovedForOrderEvent from(Long orderId, User user) {
		return UserApprovedForOrderEvent.builder()
			.orderId(orderId)
			.userSeq(user.getUserSeq())
			.build();
	}
}
