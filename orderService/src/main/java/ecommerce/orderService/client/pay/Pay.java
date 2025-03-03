package ecommerce.orderService.client.pay;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pay {

	private Long payTransactionId;
	private Long orderId;
	private Long userSeq;
	private Long payAmount;
	private PayStatus payStatus;
	private LocalDateTime payAt;
}
