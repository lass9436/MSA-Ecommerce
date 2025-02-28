package ecommerce.payService.pay.dto;

import java.time.LocalDateTime;

import ecommerce.payService.pay.domain.PayStatus;
import ecommerce.payService.pay.domain.PayTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayResponse {
	private Long payTransactionId;
	private Long orderId;
	private Long userSeq;
	private Long payAmount;
	private PayStatus payStatus;
	private LocalDateTime payAt;

	public static PayResponse from(PayTransaction transaction) {
		return PayResponse.builder()
			.payTransactionId(transaction.getPayTransactionId())
			.orderId(transaction.getOrderId())
			.userSeq(transaction.getUserSeq())
			.payAmount(transaction.getPayAmount())
			.payStatus(transaction.getPayStatus())
			.payAt(transaction.getPayAt())
			.build();
	}
}
