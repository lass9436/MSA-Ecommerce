package ecommerce.userService.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

	private Long userSeq;
	private String userId;
	private String userName;
}
