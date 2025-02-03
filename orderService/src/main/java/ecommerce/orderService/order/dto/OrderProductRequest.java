package ecommerce.orderService.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductRequest {

	@NotNull(message = "상품 ID는 비어 있을 수 없습니다.")
	private Long productId;

	@NotNull(message = "상품 수량은 비어 있을 수 없습니다.")
	@Min(value = 1, message = "상품 수량은 최소 1개 이상이어야 합니다.")
	private Long orderQuantity;

	@NotNull(message = "상점 아이디는 비어 있을 수 없습니다.")
	private Long storeId;

	@NotNull(message = "상품 가격은 비어 있을 수 없습니다.")
	private Long productPrice;
}
