package ecommerce.sellerService.seller.dto;

import ecommerce.sellerService.seller.domain.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SellerResponse {

	private Long sellerSeq;
	private String sellerId;
	private String sellerName;

	public static SellerResponse from(Seller seller) {
		return SellerResponse.builder()
			.sellerSeq(seller.getSellerSeq())
			.sellerId(seller.getSellerId())
			.sellerName(seller.getSellerName())
			.build();
	}
}
