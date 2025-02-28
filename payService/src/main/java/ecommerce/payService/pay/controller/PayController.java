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

	@PostMapping
	public ApiResult<PayResponse> requestPay(@Login LoginUser user, @RequestBody @Valid PayRequest request) {
		if (!user.getUserSeq().equals(request.getUserSeq())) {
			return ApiResult.failure(UNAUTHORIZED_ACTION, "Not authorized");
		}
		PayResponse response = payService.requestPay(request);
		return ApiResult.success(response);
	}

	@GetMapping("/{payTransactionId}")
	public ApiResult<PayResponse> findPayById(@PathVariable Long payTransactionId) {
		PayResponse response = payService.findById(payTransactionId);
		return ApiResult.success(response);
	}

}
