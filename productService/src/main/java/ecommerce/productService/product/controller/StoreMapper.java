package ecommerce.productService.product.controller;

import ecommerce.productService.product.domain.Store;

public class StoreMapper {

	private StoreMapper() {
	}

	public static Store toStore(StoreRequest storeRequest) {
		return new Store(
			storeRequest.getStoreName(),
			storeRequest.getStoreAccountNumber(),
			storeRequest.getStoreLicense(),
			storeRequest.getSellerSeq()
		);
	}

	public static StoreResponse toStoreResponse(Store store) {
		return new StoreResponse(
			store.getStoreId(),
			store.getStoreName(),
			store.getStoreAccountNumber(),
			store.getStoreLicense(),
			store.getSellerSeq()
		);
	}
}
