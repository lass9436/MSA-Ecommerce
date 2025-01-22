package ecommerce.orderService.client.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBulkIncreaseRequestDetail {

	@NotNull
	private Long productId;

	@NotNull
	@Min(value = 1, message = "증가 수량은 1 이상이어야 합니다.")
	private Long increaseQuantity;
}
