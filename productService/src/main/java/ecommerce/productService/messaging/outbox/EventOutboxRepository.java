package ecommerce.productService.messaging.outbox;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOutboxRepository extends JpaRepository<EventOutbox, Long> {
	Optional<EventOutbox> findByIdempotencyKey(String idempotencyKey);
}
