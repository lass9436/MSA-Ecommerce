package ecommerce.orderService.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.orderService.client.product.Product;
import ecommerce.orderService.client.product.ProductBulkDecreaseRequest;
import ecommerce.orderService.client.product.ProductBulkRequest;
import ecommerce.orderService.client.product.ProductClient;
import ecommerce.orderService.client.user.User;
import ecommerce.orderService.client.user.UserClient;
import ecommerce.orderService.exception.EntityNotFoundException;
import ecommerce.orderService.order.domain.Order;
import ecommerce.orderService.order.domain.OrderProduct;
import ecommerce.orderService.order.repository.OrderRepository;
import io.micrometer.common.KeyValues;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final ProductClient productClient;
	private final UserClient userClient;

	public Order registerOrder(Order order) {
		User user = userClient.getUserById(order.getUserSeq());
		List<Long> productIds = order.getOrderProducts()
			.stream()
			.map(OrderProduct::getProductId)
			.toList();
		List<Product> products = productClient.getAllProductsById(new ProductBulkRequest(productIds));
		order.calculateTotalAmount(products);
		ProductBulkDecreaseRequest productBulkDecreaseRequest = order.toBulkDecreaseRequest();
		productClient.bulkDecreaseProduct(productBulkDecreaseRequest);
		return orderRepository.save(order);
	}

	public List<Order> findAllOrders() {
		return orderRepository.findAllWithProducts();
	}

	public Order findById(Long id) {
		return orderRepository.findByIdWithProducts(id)
			.orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found."));
	}

	public Order cancelOrder(Long id) {
		Order order = orderRepository.findByIdWithProducts(id)
			.orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found."));
		productClient.bulkIncreaseProduct(order.toProductBulkIncreaseRequest());
		return order.cancel();
	}
}
