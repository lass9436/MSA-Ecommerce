package ecommerce.productService.product.dto;

import ecommerce.productService.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {

	private Long productId;
	private String productName;
	private String productCategory;
	private Integer productQuantity;
	private Long productPrice;
	private Long storeId;

	public static ProductResponse from(Product product) {
		return ProductResponse.builder()
			.productId(product.getProductId())
			.productName(product.getProductName())
			.productCategory(product.getProductCategory())
			.productQuantity(product.getProductQuantity())
			.productPrice(product.getProductPrice())
			.storeId(product.getStore().getStoreId())
			.build();
	}
}
