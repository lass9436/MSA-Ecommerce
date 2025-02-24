package ecommerce.productService.messaging.event.publish;

import ecommerce.productService.messaging.event.PublishEvent;
import ecommerce.productService.messaging.event.consume.OrderReserveProductEvent;
import lombok.Getter;

@Getter
public class ProductReservationFailedForOrderEvent extends PublishEvent {
	private final Long orderId;

	private ProductReservationFailedForOrderEvent(Long orderId) {
		super(orderId); // ApplicationEvent 의 source 로 orderId 전달
		this.orderId = orderId;
	}

	public static ProductReservationFailedForOrderEvent from(OrderReserveProductEvent event) {
		return new ProductReservationFailedForOrderEvent(event.getOrderId());
	}
}
