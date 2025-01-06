package ecommerce.productService.product.controller;

import ecommerce.productService.product.domain.Product;

public class ProductMapper {

	private ProductMapper() {
	}

	public static Product toProduct(ProductRequest productRequest) {
		return new Product(
			productRequest.getProductName(),
			productRequest.getProductCategory(),
			productRequest.getProductQuantity(),
			productRequest.getProductPrice()
		);
	}

	public static ProductResponse toProductResponse(Product product) {
		return new ProductResponse(
			product.getProductId(),
			product.getProductName(),
			product.getProductCategory(),
			product.getProductQuantity(),
			product.getProductPrice(),
			product.getStore().getStoreId()
		);
	}
}
