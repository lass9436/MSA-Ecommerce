package ecommerce.payService.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.payService.pay.domain.PayTransaction;

public interface PayTransactionRepository extends JpaRepository<PayTransaction, Long> {
}
