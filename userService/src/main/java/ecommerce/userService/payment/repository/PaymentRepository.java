package ecommerce.userService.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.userService.payment.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
