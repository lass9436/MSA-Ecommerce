package ecommerce.productService.messaging.event.produce;

import ecommerce.productService.messaging.event.ProduceEvent;
import ecommerce.productService.messaging.event.consume.OrderReserveProductEvent;
import lombok.Getter;

@Getter
public class ProductReservedForOrderEvent extends ProduceEvent {
	private final Long orderId;

	private ProductReservedForOrderEvent(Long orderId) {
		super(orderId); // ApplicationEvent 의 source 객체 전달
		this.orderId = orderId;
	}

	public static ProductReservedForOrderEvent from(OrderReserveProductEvent event) {
		return new ProductReservedForOrderEvent(event.getOrderId());
	}

}
