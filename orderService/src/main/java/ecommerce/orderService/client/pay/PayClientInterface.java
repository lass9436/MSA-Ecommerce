package ecommerce.orderService.client.pay;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ecommerce.orderService.global.ApiResult;

@FeignClient(name = "payService", url = "${api.gateway.url}")
public interface PayClientInterface {

	@GetMapping("/api/pay/{orderId}")
	ApiResult<Pay> getPayByOrderId(@PathVariable("orderId") Long orderId);
}
