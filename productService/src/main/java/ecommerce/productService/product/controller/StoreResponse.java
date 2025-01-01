package ecommerce.productService.product.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreResponse {

	private Long storeId;
	private String storeName;
	private String storeAccountNumber;
	private String storeLicense;
	private Long sellerSeq;
}
