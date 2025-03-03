package ecommerce.orderService.client.pay;

import org.springframework.stereotype.Component;

import ecommerce.orderService.global.ApiResult;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PayClient {

	private final PayClientInterface payClientInterface;

	public Pay getPayByOrderId(Long orderId) {
		ApiResult<Pay> payResult = payClientInterface.getPayByOrderId(orderId);
		if (!payResult.isSuccess()) {
			throw new RuntimeException(
				"Pay not found. ErrorCode: " + payResult.getErrorCode() + ", ErrorMessage: "
					+ payResult.getErrorMessage());
		}
		return payResult.getData();
	}
}
