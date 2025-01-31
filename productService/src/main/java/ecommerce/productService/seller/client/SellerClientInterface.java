package ecommerce.productService.seller.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ecommerce.productService.global.ApiResult;
import ecommerce.productService.seller.domain.Seller;

@FeignClient(name = "seller-service", url = "${seller.service.url}")
public interface SellerClientInterface {

	@GetMapping("/api/sellers/{sellerSeq}")
	ApiResult<Seller> getSellerById(@PathVariable("sellerSeq") Long sellerSeq);
}
