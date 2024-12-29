package ecommerce.sellerService.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellerRequest {

	private String sellerId;
	private String sellerPassword;
	private String sellerName;
}
