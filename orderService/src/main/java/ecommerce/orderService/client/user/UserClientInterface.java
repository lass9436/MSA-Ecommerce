package ecommerce.orderService.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ecommerce.orderService.global.ApiResult;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserClientInterface {

	@GetMapping("/api/users/{userSeq}")
	ApiResult<User> getUserById(@PathVariable("userSeq") Long userSeq);

}
