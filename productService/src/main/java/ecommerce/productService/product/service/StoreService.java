package ecommerce.productService.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.productService.product.domain.Store;
import ecommerce.productService.exception.EntityNotFoundException;
import ecommerce.productService.product.repository.StoreRepository;
import ecommerce.productService.seller.client.SellerClient;
import ecommerce.productService.seller.domain.Seller;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final SellerClient sellerClient;

	public Store registerStore(Store store) {
		Seller seller = sellerClient.getSellerById(store.getSellerSeq());
		return storeRepository.save(store);
	}

	public List<Store> findAllStores() {
		return storeRepository.findAll();
	}

	public Store findById(Long id) {
		return storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Store with ID " + id + " not found."));
	}

	public Store updateStore(Long id, Store updateStore) {
		Store store = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Store with ID " + id + " not found."));
		store.update(updateStore);
		return store;
	}

	public void deleteStore(Long id) {
		if(!storeRepository.existsById(id)) {
			throw new EntityNotFoundException("Store with ID " + id + " not found.");
		}
		storeRepository.deleteById(id);
	}
}
