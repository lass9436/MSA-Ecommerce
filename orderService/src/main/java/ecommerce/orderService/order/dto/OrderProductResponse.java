package ecommerce.orderService.order.dto;

import ecommerce.orderService.order.domain.OrderProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class OrderProductResponse {

	private Long orderProductId;
	private Long storeId;
	private Long productId;
	private Long orderQuantity;
	private Long productPrice;

	public static OrderProductResponse from(OrderProduct orderProduct) {
		return OrderProductResponse.builder()
			.orderProductId(orderProduct.getOrderProductId())
			.storeId(orderProduct.getStoreId())
			.productId(orderProduct.getOrderProductId())
			.orderQuantity(orderProduct.getOrderQuantity())
			.productPrice(orderProduct.getProductPrice())
			.build();
	}
}
