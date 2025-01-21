package ecommerce.orderService.client.user;

import org.springframework.stereotype.Component;

import ecommerce.orderService.exception.EntityNotFoundException;
import ecommerce.orderService.global.ApiResult;
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
}
