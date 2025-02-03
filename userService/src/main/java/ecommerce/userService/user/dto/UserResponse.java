package ecommerce.userService.user.dto;

import ecommerce.userService.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {

	private Long userSeq;
	private String userId;
	private String userName;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userSeq(user.getUserSeq())
			.userId(user.getUserId())
			.userName(user.getUserName())
			.build();
	}
}
