package ecommerce.orderService.messaging.event;

import ecommerce.orderService.order.domain.OrderProduct;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservedProductItemEvent {
	private Long productId;
	private Long orderQuantity;
	private Long storeId;
	private Long productPrice;

	public static ReservedProductItemEvent from(OrderProduct orderProduct) {
		return ReservedProductItemEvent.builder()
			.productId(orderProduct.getProductId())
			.orderQuantity(orderProduct.getOrderQuantity())
			.storeId(orderProduct.getStoreId())
			.productPrice(orderProduct.getProductPrice())
			.build();
	}
}
