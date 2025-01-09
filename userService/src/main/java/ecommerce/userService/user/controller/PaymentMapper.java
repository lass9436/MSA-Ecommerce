package ecommerce.userService.user.controller;

import ecommerce.userService.user.domain.Payment;

public class PaymentMapper {

	private PaymentMapper() {}

	public static Payment toPayment(PaymentRequest paymentRequest) {
		return new Payment(
			paymentRequest.getPaymentType(),
			paymentRequest.getPaymentName(),
			paymentRequest.getAccountNumber(),
			paymentRequest.getCardNumber()
		);
	}

	public static PaymentResponse toPaymentResponse(Payment payment) {
		return new PaymentResponse(
			payment.getPaymentId(),
			payment.getPaymentType(),
			payment.getPaymentName(),
			payment.getAccountNumber(),
			payment.getCardNumber()
		);
	}
}
