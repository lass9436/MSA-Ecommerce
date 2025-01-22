package ecommerce.orderService.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ecommerce.orderService.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o JOIN FETCH o.orderProducts WHERE o.orderId = :id")
	Optional<Order> findByIdWithProducts(@Param("id") Long id);

	@Query("SELECT o FROM Order o JOIN FETCH o.orderProducts")
	List<Order> findAllWithProducts();

}
