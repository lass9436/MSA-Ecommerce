package ecommerce.productService.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.productService.global.ApiResult;
import ecommerce.productService.product.domain.Store;
import ecommerce.productService.exception.EntityNotFoundException;
import ecommerce.productService.product.repository.StoreRepository;
import ecommerce.productService.seller.client.SellerClient;
import ecommerce.productService.seller.domain.Seller;
import io.micrometer.common.KeyValues;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final SellerClient sellerClient;

	public Store registerStore(Store store) {
		ApiResult<Seller> sellerResult = sellerClient.getSellerById(store.getSellerSeq());
		// 성공 여부 확인
		if (!sellerResult.isSuccess()) {
			throw new EntityNotFoundException(
				"Seller not found. ErrorCode: " + sellerResult.getErrorCode() +
					", ErrorMessage: " + sellerResult.getErrorMessage()
			);
		}

		Seller seller = sellerResult.getData();
		return storeRepository.save(store);
	}

	public List<Store> findAllStores() {
		return storeRepository.findAll();
	}

	public Store findById(Long id) {
		return storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Store not found."));
	}

	public Store updateStore(Long id, Store updateStore) {
		Store store = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Store not found."));
		store.update(updateStore);
		return store;
	}

	public void deleteStore(Long id) {
		storeRepository.deleteById(id);
	}
}
