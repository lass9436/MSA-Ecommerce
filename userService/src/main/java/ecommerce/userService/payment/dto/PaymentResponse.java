package ecommerce.userService.payment.dto;

import ecommerce.userService.payment.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {

	private Long paymentId;
	private String paymentType;
	private String paymentName;
	private String accountNumber;
	private String cardNumber;

	public static PaymentResponse from(Payment payment) {
		return PaymentResponse.builder()
			.paymentId(payment.getPaymentId())
			.paymentType(payment.getPaymentType())
			.paymentName(payment.getPaymentName())
			.accountNumber(payment.getAccountNumber())
			.cardNumber(payment.getCardNumber())
			.build();
	}
}
