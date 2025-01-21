package ecommerce.orderService.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_product")
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_product_id")
	private Long orderProductId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@Column(name = "store_id", nullable = false)
	private Long storeId; // 외래키 저장

	@Column(name = "product_id", nullable = false)
	private Long productId; // 외래키 저장

	@Column(name = "order_quantity", nullable = false)
	private Long orderQuantity;

	@Column(name = "product_price", nullable = false)
	private Long productPrice;

	public OrderProduct(Order order, Long storeId, Long productId, Long orderQuantity, Long productPrice) {
		this.order = order;
		this.storeId = storeId;
		this.productId = productId;
		this.orderQuantity = orderQuantity;
		this.productPrice = productPrice;
		order.addOrderProduct(this);
	}

}
