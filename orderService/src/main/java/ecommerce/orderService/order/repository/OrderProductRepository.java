package ecommerce.orderService.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.orderService.order.domain.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
