package ecommerce.orderService.client.product;

import java.util.List;

import org.springframework.stereotype.Component;

import ecommerce.orderService.exception.EntityNotFoundException;
import ecommerce.orderService.global.ApiResult;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductClient {

	private final ProductClientInterface productClientInterface;

	public Product getProductById(Long productSeq) {
		ApiResult<Product> productResult = productClientInterface.getProductById(productSeq);
		if (!productResult.isSuccess()) {
			throw new EntityNotFoundException(
				"Product not found. ErrorCode: " + productResult.getErrorCode() + ", ErrorMessage: "
					+ productResult.getErrorMessage());
		}
		return productResult.getData();
	}

	public List<Product> getAllProductsById(ProductBulkRequest productBulkRequest) {
		ApiResult<List<Product>> productResult = productClientInterface.getAllProductById(productBulkRequest);
		if (!productResult.isSuccess()) {
			throw new EntityNotFoundException(
				"Products not found. ErrorCode: " + productResult.getErrorCode() + ", ErrorMessage: "
					+ productResult.getErrorMessage());
		}
		return productResult.getData();
	}

	public void bulkDecreaseProduct(ProductBulkDecreaseRequest request) {
		ApiResult<Void> result = productClientInterface.bulkDecreaseProduct(request);
		if (!result.isSuccess()) {
			throw new IllegalStateException(
				"Failed to decrease product quantities. ErrorCode: " + result.getErrorCode() + ", ErrorMessage: "
					+ result.getErrorMessage());
		}
	}
}
