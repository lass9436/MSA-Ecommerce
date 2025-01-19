package ecommerce.productService.product.controller;

import static ecommerce.productService.product.controller.ProductMapper.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.productService.global.ApiResult;
import ecommerce.productService.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ApiResult<ProductResponse> registerProduct(@Valid @RequestBody ProductRequest productRequest) {
		return ApiResult.success(
			toProductResponse(productService.registerProduct(productRequest.getStoreId(), toProduct(productRequest))));
	}

	@GetMapping
	public ApiResult<List<ProductResponse>> findAllProducts() {
		List<ProductResponse> products = productService.findAllProducts()
			.stream()
			.map(ProductMapper::toProductResponse)
			.toList();
		return ApiResult.success(products);
	}

	@GetMapping("/{id}")
	public ApiResult<ProductResponse> findProductById(@PathVariable Long id) {
		return ApiResult.success(toProductResponse(productService.findById(id)));
	}

	@PutMapping("/{id}")
	public ApiResult<ProductResponse> updateProduct(@PathVariable Long id,
		@Valid @RequestBody ProductRequest productRequest) {
		return ApiResult.success(toProductResponse(productService.updateProduct(id, toProduct(productRequest))));
	}

	@DeleteMapping("/{id}")
	public ApiResult<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ApiResult.success(null);
	}

	@PatchMapping("/bulk-decrease")
	public ApiResult<Void> bulkDecreaseProduct(
		@Valid @RequestBody ProductBulkDecreaseRequest productBulkDecreaseRequest) {
		productService.bulkDecreaseProduct(productBulkDecreaseRequest);
		return ApiResult.success(null);
	}

}
