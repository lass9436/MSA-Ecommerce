package ecommerce.orderService.order.controller;

import static ecommerce.orderService.order.controller.OrderMapper.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.orderService.global.ApiResult;
import ecommerce.orderService.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ApiResult<OrderResponse> registerOrder(@Valid @RequestBody OrderRequest orderRequest) {
		return ApiResult.success(toOrderResponse(orderService.registerOrder(toOrder(orderRequest))));
	}

	@GetMapping
	public ApiResult<List<OrderResponse>> findAllOrders() {
		List<OrderResponse> orders = orderService.findAllOrders()
			.stream()
			.map(OrderMapper::toOrderResponse)
			.toList();
		return ApiResult.success(orders);
	}

	@GetMapping("/{id}")
	public ApiResult<OrderResponse> findOrderById(@PathVariable Long id) {
		return ApiResult.success(toOrderResponse(orderService.findById(id)));
	}

	@PatchMapping("/{id}/cancel")
	public ApiResult<OrderResponse> cancelOrder(@PathVariable Long id) {
		return ApiResult.success(toOrderResponse(orderService.cancelOrder(id)));
	}

}
