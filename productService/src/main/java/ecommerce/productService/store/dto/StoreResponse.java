package ecommerce.productService.store.dto;

import ecommerce.productService.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StoreResponse {

	private Long storeId;
	private String storeName;
	private String storeAccountNumber;
	private String storeLicense;
	private Long sellerSeq;

	public static StoreResponse from(Store store) {
		return StoreResponse.builder()
			.storeId(store.getStoreId())
			.storeName(store.getStoreName())
			.storeAccountNumber(store.getStoreAccountNumber())
			.storeLicense(store.getStoreLicense())
			.sellerSeq(store.getSellerSeq())
			.build();
	}
}
