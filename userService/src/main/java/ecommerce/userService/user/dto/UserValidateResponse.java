package ecommerce.userService.user.dto;

import ecommerce.userService.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserValidateResponse {

	private Long userSeq;
	private String userId;
	private String userName;

	public static UserValidateResponse from(User user) {
		return UserValidateResponse.builder()
			.userSeq(user.getUserSeq())
			.userId(user.getUserId())
			.userName(user.getUserName())
			.build();
	}
}
