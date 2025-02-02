package ecommerce.productService.messaging.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductReservedForOrderEvent {
	private Long orderId;

	public static ProductReservedForOrderEvent from(Long orderId) {
		return ProductReservedForOrderEvent.builder()
			.orderId(orderId)
			.build();
	}
}
