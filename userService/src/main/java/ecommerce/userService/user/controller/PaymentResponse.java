package ecommerce.userService.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {

	private Long paymentId;
	private String paymentType;
	private String paymentName;
	private String accountNumber;
	private String cardNumber;
}
