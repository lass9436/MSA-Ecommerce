package ecommerce.orderService.client.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBulkIncreaseRequest {

	private List<ProductBulkIncreaseRequestDetail> details;
}
