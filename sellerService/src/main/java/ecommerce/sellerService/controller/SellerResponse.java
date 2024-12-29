package ecommerce.sellerService.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SellerResponse {

	private Long sellerSeq;
	private String sellerId;
	private String sellerName;
}
