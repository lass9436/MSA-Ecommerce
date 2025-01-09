package ecommerce.userService.user.controller;

import static ecommerce.userService.user.controller.PaymentMapper.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.userService.global.ApiResult;
import ecommerce.userService.user.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping
	public ApiResult<PaymentResponse> registerPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
		return ApiResult.success(toPaymentResponse(paymentService.registerPayment(paymentRequest.getUserSeq(), toPayment(paymentRequest))));
	}

	@GetMapping
	public ApiResult<List<PaymentResponse>> findAllPayments() {
		List<PaymentResponse> payments = paymentService.findAllPayments()
			.stream()
			.map(PaymentMapper::toPaymentResponse)
			.toList();
		return ApiResult.success(payments);
	}

	@GetMapping("/{id}")
	public ApiResult<PaymentResponse> findPaymentById(@PathVariable Long id) {
		return ApiResult.success(toPaymentResponse(paymentService.findById(id)));
	}

	@PutMapping("/{id}")
	public ApiResult<PaymentResponse> updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentRequest paymentRequest) {
		return ApiResult.success(toPaymentResponse(paymentService.updatePayment(id, toPayment(paymentRequest))));
	}

	@DeleteMapping("/{id}")
	public ApiResult<Void> deletePayment(@PathVariable Long id) {
		paymentService.deletePayment(id);
		return ApiResult.success(null);
	}
}

