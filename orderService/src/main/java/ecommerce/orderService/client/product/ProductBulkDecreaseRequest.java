package ecommerce.orderService.client.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductBulkDecreaseRequest {

	private List<ProductBulkDecreaseRequestDetail> details;
}
