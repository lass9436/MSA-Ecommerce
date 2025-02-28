package ecommerce.payService.pay.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.payService.exception.EntityNotFoundException;
import ecommerce.payService.pay.domain.PayTransaction;
import ecommerce.payService.pay.dto.PayRequest;
import ecommerce.payService.pay.dto.PayResponse;
import ecommerce.payService.pay.repository.PayTransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PayService {

	private final PayTransactionRepository payTransactionRepository;

	public PayResponse requestPay(PayRequest request) {
		PayTransaction transaction = new PayTransaction(
			request.getOrderId(),
			request.getUserSeq(),
			request.getAmount()
		);
		transaction.paid();
		PayTransaction savedTransaction = payTransactionRepository.save(transaction);
		return PayResponse.from(savedTransaction);
	}

	public PayResponse findById(Long payTransactionId) {
		PayTransaction transaction = payTransactionRepository
			.findById(payTransactionId)
			.orElseThrow(
				() -> new EntityNotFoundException("PayTransaction with ID " + payTransactionId + " not found."));
		return PayResponse.from(transaction);
	}
}
