package ecommerce.orderService.order.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ecommerce.orderService.client.product.Product;
import ecommerce.orderService.client.product.ProductBulkDecreaseRequest;
import ecommerce.orderService.client.product.ProductBulkDecreaseRequestDetail;
import ecommerce.orderService.client.product.ProductBulkIncreaseRequest;
import ecommerce.orderService.client.product.ProductBulkIncreaseRequestDetail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false, length = 50)
	private OrderStatus orderStatus;

	@Column(name = "order_at", nullable = false)
	private LocalDateTime orderAt;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<OrderProduct> orderProducts = new ArrayList<>();

	public Order(Long userSeq, Long orderAmount, OrderStatus orderStatus, LocalDateTime orderAt) {
		this.userSeq = userSeq;
		this.orderAmount = orderAmount;
		this.orderStatus = orderStatus;
		this.orderAt = orderAt;
	}

	public void addOrderProduct(OrderProduct orderProduct) {
		this.orderProducts.add(orderProduct);
	}

	public void calculateTotalAmount(List<Product> products) {
		long totalAmount = 0L;

		for (OrderProduct orderProduct : orderProducts) {
			Product product = findProductById(products, orderProduct.getProductId());

			if (product.getProductQuantity() < orderProduct.getOrderQuantity()) {
				throw new IllegalStateException("Insufficient stock for product ID: " + product.getProductId());
			}

			if (!product.getProductPrice().equals(orderProduct.getProductPrice())) {
				throw new IllegalArgumentException("Product price mismatch for product ID: " + product.getProductId());
			}

			if (!product.getStoreId().equals(orderProduct.getStoreId())) {
				throw new IllegalArgumentException("Store ID mismatch for product ID: " + product.getProductId());
			}

			totalAmount += product.getProductPrice() * orderProduct.getOrderQuantity();
		}

		if (!this.orderAmount.equals(totalAmount)) {
			throw new IllegalArgumentException(
				"Order amount mismatch. Expected: " + totalAmount + ", Provided: " + orderAmount);
		}

		this.orderAmount = totalAmount;
	}

	public ProductBulkDecreaseRequest toBulkDecreaseRequest() {
		List<ProductBulkDecreaseRequestDetail> details = this.getOrderProducts().stream()
			.map(orderProduct -> {
				ProductBulkDecreaseRequestDetail detail = new ProductBulkDecreaseRequestDetail();
				detail.setProductId(orderProduct.getProductId());
				detail.setDecreaseQuantity(orderProduct.getOrderQuantity());
				return detail;
			})
			.toList();

		return new ProductBulkDecreaseRequest(details);
	}

	private Product findProductById(List<Product> products, Long productId) {
		return products.stream()
			.filter(product -> product.getProductId().equals(productId))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
	}

	public Order pending() {
		orderStatus = OrderStatus.ORDER_PENDING;
		return this;
	}

	public void cancel() {
		orderStatus = OrderStatus.ORDER_CANCELLED;
	}

	public ProductBulkIncreaseRequest toProductBulkIncreaseRequest() {
		List<ProductBulkIncreaseRequestDetail> details = this.getOrderProducts().stream()
			.map(p -> new ProductBulkIncreaseRequestDetail(p.getProductId(), p.getOrderQuantity()))
			.toList();
		return new ProductBulkIncreaseRequest(details);
	}

	public void complete() {
		orderStatus = OrderStatus.ORDER_COMPLETED;
	}

	public void userFailed() {
		orderStatus = OrderStatus.USER_FAILED;
	}

	public void productFailed() {
		orderStatus = OrderStatus.PRODUCT_FAILED;
	}

	public void paid() {
		if (orderStatus.equals((OrderStatus.ORDER_COMPLETED))) {
			orderStatus = OrderStatus.PAYMENT_COMPLETED;
		}
	}
}
