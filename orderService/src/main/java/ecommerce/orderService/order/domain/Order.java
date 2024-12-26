package ecommerce.orderService.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`order`")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "user_seq", nullable = false)
	private Long userSeq; // 외래키 저장

	@Column(name = "order_amount", nullable = false)
	private Long orderAmount;

	@Column(name = "order_status", nullable = false, length = 50)
	private String orderStatus;

	@Column(name = "order_at", nullable = false)
	private LocalDateTime orderAt;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderProduct> orderProducts;

}
