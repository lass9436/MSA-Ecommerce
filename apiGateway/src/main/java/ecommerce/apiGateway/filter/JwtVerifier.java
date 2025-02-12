package ecommerce.apiGateway.filter;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtVerifier {

	@Value("${jwt.secret}")
	private String jwtSecret;

	private SecretKey secretKey;

	@PostConstruct
	public void initSecretKey() {
		if (jwtSecret == null || jwtSecret.isEmpty()) {
			throw new IllegalStateException("jwtSecret 값이 유효하지 않습니다!");
		}
		secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes()); // 주입된 값으로 SecretKey 초기화
	}

	/**
	 * JWT 유효성 검증 메서드
	 *
	 * @param token 입력 JWT
	 * @return boolean (유효 여부)
	 */
	public boolean isTokenValid(String token) {
		try {
			parseToken(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Claims parseToken(String token) {
		try {
			// JWT 서명 검증 및 claims 파싱
			return Jwts.parser()
				.verifyWith(secretKey) // 서명 검증
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
