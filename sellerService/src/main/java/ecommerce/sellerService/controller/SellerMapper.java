package ecommerce.sellerService.controller;

import ecommerce.sellerService.domain.Seller;

public class SellerMapper {

	private SellerMapper() {}

	public static Seller toSeller(SellerRequest sellerRequest) {
		return new Seller(
			sellerRequest.getSellerId(),
			sellerRequest.getSellerPassword(),
			sellerRequest.getSellerName()
		);
	}

	public static SellerResponse toSellerResponse(Seller seller) {
		return new SellerResponse(
			seller.getSellerSeq(),
			seller.getSellerName(),
			seller.getSellerPassword()
		);
	}
}
