package ecommerce.payService.pay.controller;

import static ecommerce.payService.exception.ErrorCodes.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.payService.global.ApiResult;
import ecommerce.payService.global.Login;
import ecommerce.payService.global.LoginUser;
import ecommerce.payService.pay.dto.PayRequest;
import ecommerce.payService.pay.dto.PayResponse;
import ecommerce.payService.pay.service.PayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pay")
public class PayController {

	private final PayService payService;

	@PostMapping("{orderId}")
	public ApiResult<PayResponse> requestPay(@Login LoginUser user, @PathVariable Long orderId,
		@RequestBody @Valid PayRequest request) {
		if (!user.getUserSeq().equals(request.getUserSeq())) {
			return ApiResult.failure(UNAUTHORIZED_ACTION, "Not authorized");
		}
		PayResponse response = payService.requestPay(orderId, request);
		return ApiResult.success(response);
	}

	@GetMapping("/{orderId}")
	public ApiResult<PayResponse> findPayByOrderId(@PathVariable Long orderId) {
		PayResponse response = payService.findByOrderId(orderId);
		return ApiResult.success(response);
	}

}
