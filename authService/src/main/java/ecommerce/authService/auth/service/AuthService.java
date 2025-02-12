package ecommerce.authService.auth.service;

import java.util.concurrent.TimeUnit;

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
	private final RedisService redisService;

	public LoginResponse loginUser(LoginRequest loginRequest) {
		// 유저 검증
		UserValidateRequest userValidateRequest = UserValidateRequest.from(loginRequest);
		UserValidateResponse validatedUser = userClient.validateUser(userValidateRequest);
		String userId = validatedUser.getUserId();

		// 토큰 생성
		String accessToken = jwtService.generateUserAccessToken(userId);
		String refreshToken = jwtService.generateUserRefreshToken(userId);

		// 3. Refresh Token 을 Redis 에 저장
		long refreshTokenExpiration = jwtService.getTokenExpiration(refreshToken) - System.currentTimeMillis();
		redisService.save(refreshToken, userId, refreshTokenExpiration, TimeUnit.MILLISECONDS);

		// 응답에 토큰 전달
		return new LoginResponse(accessToken, refreshToken);
	}

	public void logoutUser(String accessToken, String refreshToken) {
		// 1. Access Token 블랙리스트 처리
		if (jwtService.isTokenValid(accessToken)) {
			long expiration = jwtService.getTokenExpiration(accessToken) - System.currentTimeMillis();
			redisService.save(accessToken, "blacklisted", expiration, TimeUnit.MILLISECONDS);
		}

		// 2. Refresh Token 삭제 처리
		if (redisService.exists(refreshToken)) {
			redisService.delete(refreshToken);
		} else {
			throw new IllegalStateException("Refresh Token 이 유효하지 않습니다.");
		}
	}
}
