package ecommerce.sellerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ecommerce.sellerService.domain.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
