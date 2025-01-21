package ecommerce.orderService.client.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ecommerce.orderService.global.ApiResult;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClientInterface {

	@GetMapping("/api/products/{productId}")
	ApiResult<Product> getProductById(@PathVariable("productId") Long productId);

	@PostMapping("/api/products/bulk")
	ApiResult<List<Product>> getAllProductById(@RequestBody ProductBulkRequest request);

	@PatchMapping("/api/products/bulk-decrease")
	ApiResult<Void> bulkDecreaseProduct(@RequestBody ProductBulkDecreaseRequest request);
}
