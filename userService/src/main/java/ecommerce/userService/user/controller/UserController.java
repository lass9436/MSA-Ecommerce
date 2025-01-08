package ecommerce.userService.user.controller;

import ecommerce.userService.user.service.UserService;
import ecommerce.userService.global.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ecommerce.userService.user.controller.UserMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ApiResult<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
		return ApiResult.success(toUserResponse(userService.registerUser(toUser(userRequest))));
	}

	@GetMapping
	public ApiResult<List<UserResponse>> findAllUsers() {
		List<UserResponse> users = userService.findAllUsers()
			.stream()
			.map(UserMapper::toUserResponse)
			.toList();
		return ApiResult.success(users);
	}

	@GetMapping("/{id}")
	public ApiResult<UserResponse> findUserById(@PathVariable Long id) {
		return ApiResult.success(toUserResponse(userService.findById(id)));
	}

	@PutMapping("/{id}")
	public ApiResult<UserResponse> updateUser(@PathVariable Long id,
		@Valid @RequestBody UserRequest userRequest) {
		return ApiResult.success(toUserResponse(userService.updateUser(id, toUser(userRequest))));
	}

	@DeleteMapping("/{id}")
	public ApiResult<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ApiResult.success(null);
	}
}
