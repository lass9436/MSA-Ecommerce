package ecommerce.productService.messaging.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservedProductItemEvent {
	private Long productId;
	private Long orderQuantity;
	private Long storeId;
	private Long productPrice;
}
