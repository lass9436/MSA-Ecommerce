package ecommerce.productService.messaging.event.consume;

import java.util.List;

import ecommerce.productService.messaging.event.ConsumeEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderReserveProductEvent extends ConsumeEvent {
	private String idempotencyKey;
	private Long orderId;
	private Long orderAmount;
	private List<ReservedProductItemEvent> products;
}
