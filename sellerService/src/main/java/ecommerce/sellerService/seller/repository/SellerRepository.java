package ecommerce.sellerService.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ecommerce.sellerService.seller.domain.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
