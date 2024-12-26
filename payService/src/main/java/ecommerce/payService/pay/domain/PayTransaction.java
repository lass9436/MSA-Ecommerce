package ecommerce.payService.pay.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pay_transaction")
public class PayTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pay_transaction_id")
	private Long payTransactionId;

	@Column(name = "order_id", nullable = false)
	private Long orderId; // 외래키 저장

	@Column(name = "user_seq", nullable = false)
	private Long userSeq; // 외래키 저장

	@Column(name = "pay_amount", nullable = false)
	private Long payAmount;

	@Column(name = "pay_at", nullable = false)
	private LocalDateTime payAt;

	@Column(name = "payment_type", nullable = false, length = 50)
	private String paymentType;

	@Column(name = "payment_name", nullable = false, length = 50)
	private String paymentName;

	@Column(name = "account_number", nullable = true, length = 50)
	private String accountNumber;

	@Column(name = "card_number", nullable = true, length = 50)
	private String cardNumber;

}
