package ecommerce.productService.product.controller;

import java.util.List;

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

	public static List<ProductResponse> toProductResponses(List<Product> products) {
		return products.stream()
			.map(ProductMapper::toProductResponse)
			.toList();
	}
}
