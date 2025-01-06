package ecommerce.productService.product.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

	private Long productId;
	private String productName;
	private String productCategory;
	private Integer productQuantity;
	private Long productPrice;
	private Long storeId;
}
