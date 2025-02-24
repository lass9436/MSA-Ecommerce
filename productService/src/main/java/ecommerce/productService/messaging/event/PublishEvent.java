package ecommerce.productService.messaging.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public abstract class PublishEvent extends ApplicationEvent {

	private final String idempotencyKey;

	public PublishEvent(Object source) {
		super(source);
		this.idempotencyKey = UUID.randomUUID().toString();
	}
}
