package ecommerce.authService.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String JWT_SECRET;
	private SecretKey KEY;

	private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 15; // 15분
	private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7; // 7일

	@PostConstruct
	public void initSecretKey() {
		if (JWT_SECRET == null || JWT_SECRET.isEmpty()) {
			throw new IllegalStateException("JWT_SECRET 값이 유효하지 않습니다!");
		}
		KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes()); // 주입된 값으로 SecretKey 초기화
	}


	// Access Token 생성
	public String generateAccessToken(String userId) {
		return Jwts.builder()
			.subject(userId)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
			.signWith(KEY)
			.compact();
	}

	// Refresh Token 생성
	public String generateRefreshToken(String userId) {
		return Jwts.builder()
			.subject(userId)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
			.signWith(KEY)
			.compact();
	}

	// JWT 파싱 및 검증
	public Claims parseToken(String token) {
		try {
			// JWT 서명 검증 및 claims 파싱
			return Jwts.parser()
				.verifyWith(KEY) // 서명 검증
				.build()
				.parseSignedClaims(token) // claims 가져오기
				.getPayload(); // JWT 서명의 본문을 반환
		} catch (JwtException e) {
			// 유효하지 않은 JWT 일 경우 예외 처리
			throw new RuntimeException("유효하지 않은 JWT 입니다.", e);
		}
	}


	// JWT 만료 여부 확인
	public boolean isTokenExpired(String token) {
		Date expiration = parseToken(token).getExpiration();
		return expiration.before(new Date());
	}

}
