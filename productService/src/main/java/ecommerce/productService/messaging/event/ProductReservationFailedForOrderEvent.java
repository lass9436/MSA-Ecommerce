package ecommerce.productService.messaging.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductReservationFailedForOrderEvent {
	private Long orderId;

	public static ProductReservationFailedForOrderEvent from(OrderReserveProductEvent event) {
		return ProductReservationFailedForOrderEvent.builder()
			.orderId(event.getOrderId())
			.build();
	}
}
