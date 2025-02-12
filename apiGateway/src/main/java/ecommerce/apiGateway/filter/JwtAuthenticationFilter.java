package ecommerce.apiGateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

	private final JwtVerifier jwtVerifier;

	private static final String AUTH_HEADER = "Authorization"; // JWT 가 포함된 요청 헤더 이름
	private static final String BEARER_PREFIX = "Bearer ";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String authHeader = exchange.getRequest().getHeaders().getFirst(AUTH_HEADER);
		if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
			String token = authHeader.substring(BEARER_PREFIX.length());
			try {
				if (jwtVerifier.isTokenValid(token)) {
					Claims claims = jwtVerifier.parseToken(token);
					String userId = claims.getSubject();
					String role = claims.get("role", String.class);

					ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
						.header("X-User-Id", userId) // 사용자 ID를 추가
						.header("X-User-Role", role) // 역할을 추가
						.build();

					return chain.filter(exchange.mutate().request(modifiedRequest).build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chain.filter(exchange);
	}
}
