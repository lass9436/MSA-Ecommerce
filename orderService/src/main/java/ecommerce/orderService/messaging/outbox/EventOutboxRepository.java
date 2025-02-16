package ecommerce.orderService.messaging.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOutboxRepository extends JpaRepository<EventOutbox, Long> {
}
