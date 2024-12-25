package ecommerce.productService.seller.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seller")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seller_seq")
	private Long sellerSeq;

	@Column(name = "seller_id", nullable = false, length = 50, unique = true)
	private String sellerId;

	@Column(name = "seller_password", nullable = false, length = 50)
	private String sellerPassword;

	@Column(name = "seller_name", nullable = false, length = 50)
	private String sellerName;
}