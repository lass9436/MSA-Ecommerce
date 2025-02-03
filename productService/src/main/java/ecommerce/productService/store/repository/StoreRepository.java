package ecommerce.productService.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.productService.store.domain.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
