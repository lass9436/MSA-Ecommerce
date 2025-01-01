package ecommerce.productService.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.productService.product.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
