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
			.orElseThrow(() -> new EntityNotFoundException("Store with ID " + storeId + " not found."));
		product.assignStore(store);
		return productRepository.save(product);
	}

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found."));
	}

	public Product updateProduct(Long id, Product updateProduct) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found."));
		product.update(updateProduct);
		return product;
	}

	public void deleteProduct(Long id) {
		if (!productRepository.existsById(id)) {
			throw new EntityNotFoundException("Product with ID " + id + " not found.");
		}
		productRepository.deleteById(id);
	}
}
