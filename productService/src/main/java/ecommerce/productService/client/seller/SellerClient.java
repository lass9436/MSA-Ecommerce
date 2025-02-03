package ecommerce.productService.client.seller;

import org.springframework.stereotype.Component;

import ecommerce.productService.exception.EntityNotFoundException;
import ecommerce.productService.global.ApiResult;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SellerClient {

	private final SellerClientInterface sellerClientInterface;

	public Seller getSellerById(Long sellerSeq) {
		ApiResult<Seller> sellerResult = sellerClientInterface.getSellerById(sellerSeq);
		if (!sellerResult.isSuccess()) {
			throw new EntityNotFoundException(
				"Seller not found. ErrorCode: " + sellerResult.getErrorCode() +
					", ErrorMessage: " + sellerResult.getErrorMessage()
			);
		}
		return sellerResult.getData();
	}
}
