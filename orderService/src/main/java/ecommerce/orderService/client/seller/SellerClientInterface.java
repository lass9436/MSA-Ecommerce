package ecommerce.orderService.client.seller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ecommerce.orderService.global.ApiResult;

@FeignClient(name = "seller-service", url = "${api.gateway.url}")
public interface SellerClientInterface {

	@GetMapping("/api/sellers/{sellerSeq}")
	ApiResult<Seller> getSellerById(@PathVariable("sellerSeq") Long sellerSeq);
}
