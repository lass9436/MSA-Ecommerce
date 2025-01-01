package ecommerce.productService.product.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ecommerce.productService.product.domain.Store;
import ecommerce.productService.product.exception.EntityNotFoundException;
import ecommerce.productService.product.repository.StoreRepository;
import ecommerce.productService.seller.client.SellerClient;
import ecommerce.productService.seller.domain.Seller;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final SellerClient sellerClient;

	public Store registerStore(Store store) {
		Optional<Seller> seller = sellerClient.getSellerById(store.getSellerSeq());
		seller.orElseThrow(() -> new EntityNotFoundException("Seller not found"));
		return storeRepository.save(store);
	}
}
