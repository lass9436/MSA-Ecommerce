package ecommerce.orderService.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.orderService.global.ApiResult;
import ecommerce.orderService.order.dto.OrderRequest;
import ecommerce.orderService.order.dto.OrderResponse;
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
		return ApiResult.success(orderService.registerOrder(orderRequest));
	}

	@GetMapping
	public ApiResult<List<OrderResponse>> findAllOrders() {
		return ApiResult.success(orderService.findAllOrders());
	}

	@GetMapping("/{id}")
	public ApiResult<OrderResponse> findOrderById(@PathVariable Long id) {
		return ApiResult.success(orderService.findById(id));
	}

	@PatchMapping("/{id}/cancel")
	public ApiResult<OrderResponse> cancelOrder(@PathVariable Long id) {
		return ApiResult.success(orderService.cancelOrder(id));
	}

	@PatchMapping("{id}/paid")
	public ApiResult<Void> paidOrder(@PathVariable Long id) {
		orderService.paidOrder(id);
		return ApiResult.success(null);
	}
}
