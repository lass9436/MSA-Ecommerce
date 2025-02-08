package ecommerce.authService.client.user;

import ecommerce.authService.auth.dto.LoginRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserValidateRequest {

	public String userId;
	public String userPassword;

	public static UserValidateRequest from(LoginRequest loginRequest) {
		return UserValidateRequest.builder()
			.userId(loginRequest.getUserId())
			.userPassword(loginRequest.getUserPassword())
			.build();
	}
}
