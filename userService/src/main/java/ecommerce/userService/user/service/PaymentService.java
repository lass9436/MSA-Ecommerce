package ecommerce.userService.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.userService.exception.EntityNotFoundException;
import ecommerce.userService.user.domain.Payment;
import ecommerce.userService.user.domain.User;
import ecommerce.userService.user.repository.PaymentRepository;
import ecommerce.userService.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;

	public Payment registerPayment(Long userId, Payment payment) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));
		payment.assignUser(user);
		return paymentRepository.save(payment);
	}

	public List<Payment> findAllPayments() {
		return paymentRepository.findAll();
	}

	public Payment findById(Long id) {
		return paymentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Payment with ID " + id + " not found."));
	}

	public Payment updatePayment(Long id, Payment updatePayment) {
		Payment payment = paymentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Payment with ID " + id + " not found."));
		payment.update(updatePayment);
		return payment;
	}

	public void deletePayment(Long id) {
		if (!paymentRepository.existsById(id)) {
			throw new EntityNotFoundException("Payment with ID " + id + " not found.");
		}
		paymentRepository.deleteById(id);
	}
}
