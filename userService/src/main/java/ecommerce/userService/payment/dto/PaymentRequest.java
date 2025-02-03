package ecommerce.userService.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest {

	@NotBlank(message = "결제 타입은 비어 있을 수 없습니다.")
	@Size(min = 2, max = 50, message = "결제 타입은 2자 이상 50자 이하이어야 합니다.")
	private String paymentType;

	@NotBlank(message = "결제 이름은 비어 있을 수 없습니다.")
	@Size(min = 2, max = 50, message = "결제 이름은 2자 이상 50자 이하이어야 합니다.")
	private String paymentName;

	@Size(min = 10, max = 50, message = "계좌 번호는 10자 이상 50자 이하이어야 합니다.")
	private String accountNumber;

	@Size(min = 10, max = 50, message = "카드 번호는 10자 이상 50자 이하이어야 합니다.")
	private String cardNumber;

	@NotNull(message = "판매자 시퀀스는 비어 있을 수 없습니다.")
	private Long userSeq;
}
