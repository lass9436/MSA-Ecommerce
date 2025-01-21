package ecommerce.orderService.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.orderService.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
