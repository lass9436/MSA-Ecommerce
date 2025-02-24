package ecommerce.productService.messaging.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public abstract class ProduceEvent extends ApplicationEvent {

	private final String idempotencyKey;

	public ProduceEvent(Object source) {
		super(source);
		this.idempotencyKey = UUID.randomUUID().toString();
	}
}
