package ecommerce.authService.client.user;

import org.springframework.stereotype.Component;

import ecommerce.authService.exception.EntityNotFoundException;
import ecommerce.authService.global.ApiResult;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClient {

	private final UserClientInterface userClientInterface;

	public User getUserById(Long userSeq) {
		ApiResult<User> userResult = userClientInterface.getUserById(userSeq);
		if (!userResult.isSuccess()) {
			throw new EntityNotFoundException(
				"User not found. ErrorCode: " + userResult.getErrorCode() + ", ErrorMessage: "
					+ userResult.getErrorMessage());
		}
		return userResult.getData();
	}

	public UserValidateResponse validateUser(UserValidateRequest userValidateRequest) {
		ApiResult<UserValidateResponse> userResult = userClientInterface.loginUser(userValidateRequest);
		if (!userResult.isSuccess()) {
			throw new IllegalArgumentException(
				"User not found. ErrorCode: " + userResult.getErrorCode() + ", ErrorMessage: "
					+ userResult.getErrorMessage());
		}
		return userResult.getData();
	}
}
