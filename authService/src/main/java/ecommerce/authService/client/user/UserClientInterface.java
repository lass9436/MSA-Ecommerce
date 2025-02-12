package ecommerce.authService.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ecommerce.authService.global.ApiResult;

@FeignClient(name = "user-service", url = "${api.gateway.url}")
public interface UserClientInterface {

	@GetMapping("/api/users/{userSeq}")
	ApiResult<User> getUserById(@PathVariable("userSeq") Long userSeq);

	@PostMapping("/api/users/validate")
	ApiResult<UserValidateResponse> loginUser(@RequestBody UserValidateRequest userValidateRequest);
}
