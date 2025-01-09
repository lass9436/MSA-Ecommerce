package ecommerce.userService.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_seq", nullable = false)
	private User user;

	@Column(nullable = false, length = 50)
	private String paymentType;

	@Column(nullable = false, length = 50)
	private String paymentName;

	@Column(length = 50)
	private String accountNumber;

	@Column(length = 50)
	private String cardNumber;

	public Payment(String paymentType, String paymentName, String accountNumber, String cardNumber) {
		this.paymentType = paymentType;
		this.paymentName = paymentName;
		this.accountNumber = accountNumber;
		this.cardNumber = cardNumber;
	}

	public void update(Payment updatePayment) {
		this.paymentType = updatePayment.getPaymentType();
		this.paymentName = updatePayment.getPaymentName();
		this.accountNumber = updatePayment.getAccountNumber();
		this.cardNumber = updatePayment.getCardNumber();
	}

	public void assignUser(User user) {
		this.user = user;
	}
}
