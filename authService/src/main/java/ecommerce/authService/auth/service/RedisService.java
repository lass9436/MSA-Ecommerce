package ecommerce.authService.auth.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	// 토큰 저장 (TTL 설정)
	public void save(String key, String value, long timeout, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	// 토큰 조회
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	// 토큰 존재 여부 확인
	public boolean exists(String key) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	// 토큰 삭제r
	public void delete(String key) {
		redisTemplate.delete(key);
	}

}
