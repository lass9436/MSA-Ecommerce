package ecommerce.userService.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
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

	@Column(nullable = false, length = 50)
	private String accountNumber;

	@Column(nullable = false, length = 50)
	private String cardNumber;

}
