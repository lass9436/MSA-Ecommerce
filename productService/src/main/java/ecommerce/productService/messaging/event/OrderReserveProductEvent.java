package ecommerce.productService.messaging.event;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderReserveProductEvent {
	private Long orderId;
	private Long orderAmount;
	private List<ReservedProductItemEvent> products;
}
