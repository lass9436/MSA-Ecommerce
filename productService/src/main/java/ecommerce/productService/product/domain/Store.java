package ecommerce.productService.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_id")
	private Long storeId;

	@Column(name = "store_name", nullable = false, length = 50)
	private String storeName;

	@Column(name = "store_account_number", nullable = false, length = 50)
	private String storeAccountNumber;

	@Column(name = "store_license", nullable = false, length = 50)
	private String storeLicense;

	@Column(name = "seller_seq", nullable = false)
	private Long sellerSeq;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products;

	public Store(String storeName, String storeAccountNumber, String storeLicense, Long sellerSeq) {
		this.storeName = storeName;
		this.storeAccountNumber = storeAccountNumber;
		this.storeLicense = storeLicense;
		this.sellerSeq = sellerSeq;
	}

}
