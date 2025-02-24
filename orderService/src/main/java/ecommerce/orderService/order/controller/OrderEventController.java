package ecommerce.orderService.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.orderService.global.ApiResult;
import ecommerce.orderService.messaging.event.facade.OrderEventFacade;
import ecommerce.orderService.order.dto.OrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderEventController {

	private final OrderEventFacade orderEventFacade;

	@PostMapping("/async")
	public ApiResult<Void> asyncRegisterOrder(@Valid @RequestBody OrderRequest orderRequest) {
		orderEventFacade.asyncRegisterOrder(orderRequest);
		return ApiResult.success(null);
	}
}
