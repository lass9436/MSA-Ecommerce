package ecommerce.payService.webhook.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ecommerce.payService.global.ApiResult;

@FeignClient(name = "orderService", url = "${api.gateway.url}")
public interface OrderClientInterface {

	@PatchMapping("/api/orders/{orderId}/paid")
	ApiResult<Void> paid(@PathVariable("orderId") Long orderId);
}
