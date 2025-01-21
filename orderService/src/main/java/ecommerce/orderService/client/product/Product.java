package ecommerce.orderService.client.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {

	private Long productId;
	private String productName;
	private String productCategory;
	private Integer productQuantity;
	private Long productPrice;
	private Long storeId;
}
