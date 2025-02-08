package ecommerce.authService.auth.service;

import org.springframework.stereotype.Service;

import ecommerce.authService.auth.dto.LoginRequest;
import ecommerce.authService.auth.dto.LoginResponse;
import ecommerce.authService.client.user.UserClient;
import ecommerce.authService.client.user.UserValidateRequest;
import ecommerce.authService.client.user.UserValidateResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserClient userClient;
	private final JwtService jwtService;

	public LoginResponse loginUser(LoginRequest loginRequest) {
		// 유저 검증
		UserValidateRequest userValidateRequest = UserValidateRequest.from(loginRequest);
		UserValidateResponse validatedUser = userClient.validateUser(userValidateRequest);
		String userId = validatedUser.getUserId();

		// 토큰 생성
		String accessToken = jwtService.generateAccessToken(userId);
		String refreshToken = jwtService.generateRefreshToken(userId);

		// 응답에 토큰 전달
		return new LoginResponse(accessToken, refreshToken);
	}
}
