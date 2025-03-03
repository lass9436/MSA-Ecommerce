package ecommerce.payService.pay.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.payService.exception.EntityNotFoundException;
import ecommerce.payService.pay.domain.PayTransaction;
import ecommerce.payService.pay.dto.PayRequest;
import ecommerce.payService.pay.dto.PayResponse;
import ecommerce.payService.pay.repository.PayTransactionRepository;
import ecommerce.payService.webhook.event.PayCompletedEvent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PayService {

	private final PayTransactionRepository payTransactionRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	public PayResponse requestPay(Long orderId, PayRequest request) {
		PayTransaction transaction = new PayTransaction(
			orderId,
			request.getUserSeq(),
			request.getAmount()
		);
		transaction.paid();
		PayTransaction savedTransaction = payTransactionRepository.save(transaction);
		applicationEventPublisher.publishEvent(PayCompletedEvent.from(transaction));
		return PayResponse.from(savedTransaction);
	}

	public PayResponse findByOrderId(Long orderId) {
		PayTransaction transaction = payTransactionRepository
			.findById(orderId)
			.orElseThrow(
				() -> new EntityNotFoundException("PayTransaction with Order ID " + orderId + " not found."));
		return PayResponse.from(transaction);
	}
}
