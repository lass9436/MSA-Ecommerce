package ecommerce.orderService.client.product;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductBulkRequest {

	@NotNull
	@Size(min = 1, message = "상품 ID 목록은 최소 1개 이상의 요소를 포함해야 합니다.")
	private List<Long> productIds;
}
