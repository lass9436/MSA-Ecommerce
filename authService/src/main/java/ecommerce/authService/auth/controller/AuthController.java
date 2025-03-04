package ecommerce.authService.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.authService.auth.dto.LoginRequest;
import ecommerce.authService.auth.dto.LoginResponse;
import ecommerce.authService.auth.dto.LogoutRequest;
import ecommerce.authService.auth.service.AuthService;
import ecommerce.authService.global.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ApiResult<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
		return ApiResult.success(authService.loginUser(loginRequest));
	}

	@PostMapping("/logout")
	public ApiResult<Void> logout(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody LogoutRequest logoutRequest) {
		accessToken = accessToken.replace("Bearer ", "");
		authService.logoutUser(accessToken, logoutRequest.getRefreshToken());
		return ApiResult.success(null);
	}
}
