package ecommerce.productService.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "product_name", nullable = false, length = 50)
	private String productName;

	@Column(name = "product_category", nullable = false, length = 50)
	private String productCategory;

	@Column(name = "product_quantity", nullable = false)
	private Integer productQuantity;

	@Column(name = "product_price", nullable = false)
	private Long productPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	public Product(String productName, String productCategory, Integer productQuantity, Long productPrice) {
		this.productName = productName;
		this.productCategory = productCategory;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
	}

	public void update(Product updateProduct) {
		this.productName = updateProduct.getProductName();
		this.productCategory = updateProduct.getProductCategory();
		this.productQuantity = updateProduct.getProductQuantity();
		this.productPrice = updateProduct.getProductPrice();
	}

	public void assignStore(Store store) {
		this.store = store;
		store.getProducts().add(this);
	}

	public void decreaseStock(Long quantity) {
		if (this.productQuantity < quantity) {
			throw new IllegalArgumentException("Insufficient stock for Product ID " + this.productId);
		}
		this.productQuantity -= quantity.intValue();
	}

	public void increaseStock(Long quantity) {
		this.productQuantity += quantity.intValue();
	}
}

