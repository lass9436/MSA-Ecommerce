package ecommerce.userService.user.controller;

import ecommerce.userService.user.domain.User;

public class UserMapper {

	private UserMapper() {}

	public static User toUser(UserRequest userRequest) {
		return new User(
			userRequest.getUserId(),
			userRequest.getUserPassword(),
			userRequest.getUserName()
		);
	}

	public static UserResponse toUserResponse(User user) {
		return new UserResponse(
			user.getUserSeq(),
			user.getUserId(),
			user.getUserName()
		);
	}
}
