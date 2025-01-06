package ecommerce.productService.seller.client;

import org.springframework.stereotype.Service;

import ecommerce.productService.exception.EntityNotFoundException;
import ecommerce.productService.global.ApiResult;
import ecommerce.productService.seller.domain.Seller;
import lombok.RequiredArgsConstructor;

@Service
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
