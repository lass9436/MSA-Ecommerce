package ecommerce.productService.product.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductBulkIncreaseRequest {

	private List<ProductBulkIncreaseRequestDetail> details;
}
