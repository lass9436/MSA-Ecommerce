package ecommerce.productService.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.productService.exception.EntityNotFoundException;
import ecommerce.productService.product.domain.Product;
import ecommerce.productService.product.domain.Store;
import ecommerce.productService.product.repository.ProductRepository;
import ecommerce.productService.product.repository.StoreRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;

	public Product registerProduct(Long storeId, Product product) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new EntityNotFoundException("Store not found."));
		product.assignStore(store);
		return productRepository.save(product);
	}

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Product not found."));
	}

	public Product updateProduct(Long id, Long storeId, Product updateProduct) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Product not found."));

		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new EntityNotFoundException("Store not found."));

		product.update(updateProduct, store);
		return product;
	}

	public void deleteProduct(Long id) {
		if (!productRepository.existsById(id)) {
			throw new EntityNotFoundException("Product not found.");
		}
		productRepository.deleteById(id);
	}
}
