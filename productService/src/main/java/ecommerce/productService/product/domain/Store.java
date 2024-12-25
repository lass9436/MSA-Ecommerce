package ecommerce.productService.product.domain;

import ecommerce.productService.seller.domain.Seller;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_seq", nullable = false)
	private Seller seller;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products;

}
