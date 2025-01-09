package ecommerce.userService.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.userService.user.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
