package ecommerce.userService.payment.controller;

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
import ecommerce.userService.payment.dto.PaymentRequest;
import ecommerce.userService.payment.dto.PaymentResponse;
import ecommerce.userService.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping
	public ApiResult<PaymentResponse> registerPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
		return ApiResult.success(paymentService.registerPayment(paymentRequest));
	}

	@GetMapping
	public ApiResult<List<PaymentResponse>> findAllPayments() {
		return ApiResult.success(paymentService.findAllPayments());
	}

	@GetMapping("/{id}")
	public ApiResult<PaymentResponse> findPaymentById(@PathVariable Long id) {
		return ApiResult.success(paymentService.findById(id));
	}

	@PutMapping("/{id}")
	public ApiResult<PaymentResponse> updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentRequest paymentRequest) {
		return ApiResult.success(paymentService.updatePayment(id, paymentRequest));
	}

	@DeleteMapping("/{id}")
	public ApiResult<Void> deletePayment(@PathVariable Long id) {
		paymentService.deletePayment(id);
		return ApiResult.success(null);
	}
}

