package ecommerce.payService.webhook.event;

import ecommerce.payService.pay.domain.PayTransaction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PayCompletedEvent {

	private Long orderId;
	private Long userSeq;
	private Long amount;

	public static PayCompletedEvent from(PayTransaction transaction) {
		return PayCompletedEvent.builder()
			.orderId(transaction.getOrderId())
			.userSeq(transaction.getUserSeq())
			.amount(transaction.getPayAmount())
			.build();
	}
}
