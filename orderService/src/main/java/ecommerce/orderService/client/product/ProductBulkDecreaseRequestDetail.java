package ecommerce.orderService.client.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductBulkDecreaseRequestDetail {

	@NotNull
	private Long productId;

	@NotNull
	@Min(value = 1, message = "차감 수량은 1 이상이어야 합니다.")
	private Long decreaseQuantity;
}
