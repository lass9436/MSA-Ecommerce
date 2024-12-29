package ecommerce.sellerService.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	public Seller(String sellerId, String sellerPassword, String sellerName) {
		this.sellerId = sellerId;
		this.sellerPassword = sellerPassword;
		this.sellerName = sellerName;
	}

	public void update(Seller updateSeller) {
		this.sellerId = updateSeller.getSellerId();
		this.sellerPassword = updateSeller.getSellerPassword();
		this.sellerName = updateSeller.getSellerName();
	}
}
