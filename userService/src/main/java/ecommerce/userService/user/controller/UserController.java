package ecommerce.userService.user.controller;

import ecommerce.userService.user.dto.UserRequest;
import ecommerce.userService.user.dto.UserResponse;
import ecommerce.userService.user.dto.UserUpdateRequest;
import ecommerce.userService.user.dto.UserValidateRequest;
import ecommerce.userService.user.dto.UserValidateResponse;
import ecommerce.userService.user.service.UserService;
import ecommerce.userService.global.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ApiResult<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
		return ApiResult.success(userService.registerUser(userRequest));
	}

	@GetMapping
	public ApiResult<List<UserResponse>> findAllUsers() {
		return ApiResult.success(userService.findAllUsers());
	}

	@GetMapping("/{id}")
	public ApiResult<UserResponse> findUserById(@PathVariable Long id) {
		return ApiResult.success(userService.findById(id));
	}

	@PutMapping("/{id}")
	public ApiResult<UserResponse> updateUser(@PathVariable Long id,
		@Valid @RequestBody UserUpdateRequest userRequest) {
		return ApiResult.success(userService.updateUser(id, userRequest));
	}

	@DeleteMapping("/{id}")
	public ApiResult<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ApiResult.success(null);
	}

	@PostMapping("/validate")
	public ApiResult<UserValidateResponse> validateUser(@Valid @RequestBody UserValidateRequest userValidateRequest) {
		return ApiResult.success(userService.validateUser(userValidateRequest));
	}
}
