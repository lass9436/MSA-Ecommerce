package ecommerce.productService.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.productService.exception.EntityNotFoundException;
import ecommerce.productService.product.controller.ProductBulkDecreaseRequest;
import ecommerce.productService.product.controller.ProductBulkDecreaseRequestDetail;
import ecommerce.productService.product.controller.ProductBulkIncreaseRequest;
import ecommerce.productService.product.controller.ProductBulkIncreaseRequestDetail;
import ecommerce.productService.product.controller.ProductBulkRequest;
import ecommerce.productService.product.domain.Product;
import ecommerce.productService.product.domain.Store;
import ecommerce.productService.product.repository.ProductRepository;
import ecommerce.productService.product.repository.StoreRepository;
import jakarta.validation.Valid;
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

	public void bulkDecreaseProduct(ProductBulkDecreaseRequest productBulkDecreaseRequest) {
		List<Long> productIds = productBulkDecreaseRequest.getDetails().stream()
			.map(ProductBulkDecreaseRequestDetail::getProductId)
			.toList();

		List<Product> products = productRepository.findByProductIdsWithLock(productIds);

		if (products.size() != productIds.size()) {
			throw new EntityNotFoundException("One or more Product IDs not found.");
		}

		for (ProductBulkDecreaseRequestDetail detail : productBulkDecreaseRequest.getDetails()) {
			Product product = products.stream()
				.filter(p -> p.getProductId().equals(detail.getProductId()))
				.findFirst()
				.orElseThrow(
					() -> new EntityNotFoundException("Product with ID " + detail.getProductId() + " not found."));

			product.decreaseStock(detail.getDecreaseQuantity());
		}
	}

	public List<Product> findAllById(ProductBulkRequest productBulkRequest) {
		return productRepository.findAllById(productBulkRequest.getProductIds());
	}

	public void bulkIncreaseProduct(@Valid ProductBulkIncreaseRequest productBulkIncreaseRequest) {
		List<Long> productIds = productBulkIncreaseRequest.getDetails().stream()
			.map(ProductBulkIncreaseRequestDetail::getProductId)
			.toList();

		List<Product> products = productRepository.findByProductIdsWithLock(productIds);

		products.forEach(product -> productBulkIncreaseRequest.getDetails().stream()
			.filter(detail -> detail.getProductId().equals(product.getProductId()))
			.findFirst()
			.ifPresent(detail -> product.increaseStock(detail.getIncreaseQuantity()))
		);
	}
}
