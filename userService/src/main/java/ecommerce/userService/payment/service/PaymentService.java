package ecommerce.userService.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.userService.exception.EntityNotFoundException;
import ecommerce.userService.payment.dto.PaymentRequest;
import ecommerce.userService.payment.dto.PaymentResponse;
import ecommerce.userService.payment.domain.Payment;
import ecommerce.userService.user.domain.User;
import ecommerce.userService.payment.repository.PaymentRepository;
import ecommerce.userService.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
	private final UserRepository userRepository;

	/**
	 * 결제 정보를 등록하는 메서드
	 *
	 * @param paymentRequest 결제 정보를 담고 있는 요청 객체
	 * @return 등록된 결제 정보에 대한 응답 객체
	 */
	public PaymentResponse registerPayment(PaymentRequest paymentRequest) {
		// 사용자 정보 조회
		// paymentRequest 에서 전달된 userSeq 로 사용자를 찾고, 없으면 예외 발생
		User user = userRepository.findById(paymentRequest.getUserSeq())
			.orElseThrow(
				() -> new EntityNotFoundException("User with ID " + paymentRequest.getUserSeq() + " not found."));

		// 결제 객체 생성
		// Payment 객체를 생성하고, 요청에 포함된 결제 정보를 설정
		Payment payment = new Payment(paymentRequest.getPaymentType(), paymentRequest.getPaymentName(),
			paymentRequest.getAccountNumber(), paymentRequest.getCardNumber());

		// 사용자와 결제 정보 연결
		// 결제 정보에 사용자 할당
		payment.assignUser(user);

		// 결제 정보 저장
		// 결제 정보를 데이터베이스에 저장
		paymentRepository.save(payment);

		// 결제 정보 응답 객체 반환
		// 저장된 결제 정보를 PaymentResponse 로 변환하여 반환
		return PaymentResponse.from(payment);
	}

	/**
	 * 모든 결제 정보를 조회하는 메서드
	 *
	 * @return 결제 정보 목록에 대한 응답 객체 리스트
	 */
	public List<PaymentResponse> findAllPayments() {
		// 모든 결제 정보를 조회하여 응답 객체로 변환
		return paymentRepository.findAll().stream()
			.map(PaymentResponse::from)
			.toList();
	}

	/**
	 * 결제 ID로 결제 정보를 조회하는 메서드
	 *
	 * @param id 결제 정보를 조회할 결제의 ID
	 * @return 결제 정보에 대한 응답 객체
	 */
	public PaymentResponse findById(Long id) {
		// 결제 ID로 결제 정보를 조회하고, 없으면 예외 발생
		Payment payment = paymentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Payment with ID " + id + " not found."));
		// 조회된 결제 정보를 응답 객체로 변환하여 반환
		return PaymentResponse.from(payment);
	}

	/**
	 * 결제 정보를 수정하는 메서드
	 *
	 * @param id 결제 ID
	 * @param paymentRequest 수정할 결제 정보를 담고 있는 요청 객체
	 * @return 수정된 결제 정보에 대한 응답 객체
	 */
	public PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest) {
		// 결제 ID로 결제 정보를 조회하고, 없으면 예외 발생
		Payment payment = paymentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Payment with ID " + id + " not found."));

		// 결제 정보 업데이트
		payment.update(paymentRequest.getPaymentType(), paymentRequest.getPaymentName(),
			paymentRequest.getAccountNumber(), paymentRequest.getCardNumber());

		// 수정된 결제 정보를 응답 객체로 변환하여 반환
		return PaymentResponse.from(payment);
	}

	/**
	 * 결제 정보를 삭제하는 메서드
	 *
	 * @param id 삭제할 결제의 ID
	 * @throws EntityNotFoundException 결제 정보가 존재하지 않으면 예외 발생
	 */
	public void deletePayment(Long id) {
		// 결제 ID로 결제 정보 조회, 없으면 EntityNotFoundException 발생
		Payment payment = paymentRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Payment with ID " + id + " not found."));

		// 결제 정보 삭제
		paymentRepository.delete(payment);
	}
}
