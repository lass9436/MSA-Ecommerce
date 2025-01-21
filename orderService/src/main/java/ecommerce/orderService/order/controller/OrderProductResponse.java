package ecommerce.orderService.order.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductResponse {

	private Long orderProductId;
	private Long storeId;
	private Long productId;
	private Long orderQuantity;
	private Long productPrice;
}
