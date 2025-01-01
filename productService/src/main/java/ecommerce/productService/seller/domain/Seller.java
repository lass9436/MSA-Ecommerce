package ecommerce.productService.seller.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Seller {

	private Long sellerSeq;
	private String sellerId;
	private String sellerName;
}
