package ecommerce.payService.pay.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "pay_status", nullable = false)
	private PayStatus payStatus;

	@Column(name = "pay_at", nullable = false)
	private LocalDateTime payAt;

	public PayTransaction(Long orderId, Long userSeq, Long payAmount) {
		this.orderId = orderId;
		this.userSeq = userSeq;
		this.payAmount = payAmount;
		this.payStatus = PayStatus.PENDING;
		this.payAt = LocalDateTime.now();
	}

	public void paid() {
		payStatus = PayStatus.SUCCESS;
	}
}
