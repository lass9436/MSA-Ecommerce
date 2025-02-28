package ecommerce.payService.pay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRequest {

	@NotNull
	@Positive
	private Long orderId;

	@NotNull
	@Positive
	private Long userSeq;

	@NotNull
	@Positive
	private Long amount;
}
