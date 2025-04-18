package ecommerce.orderService.messaging.event.produce;

import ecommerce.orderService.order.dto.OrderProductResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservedProductItemEvent {
	private Long productId;
	private Long orderQuantity;
	private Long storeId;
	private Long productPrice;

	public static ReservedProductItemEvent from(OrderProductResponse orderProduct) {
		return ReservedProductItemEvent.builder()
			.productId(orderProduct.getProductId())
			.orderQuantity(orderProduct.getOrderQuantity())
			.storeId(orderProduct.getStoreId())
			.productPrice(orderProduct.getProductPrice())
			.build();
	}
}
