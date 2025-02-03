package ecommerce.userService.user.service;

import ecommerce.userService.messaging.event.OrderPendingEvent;
import ecommerce.userService.messaging.event.UserApprovedForOrderEvent;
import ecommerce.userService.messaging.producer.UserEventProducer;
import ecommerce.userService.user.dto.UserRequest;
import ecommerce.userService.user.dto.UserResponse;
import ecommerce.userService.user.domain.User;
import ecommerce.userService.exception.EntityNotFoundException;
import ecommerce.userService.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final UserEventProducer userEventProducer;

	/**
	 * 사용자 등록 메서드
	 *
	 * @param userRequest 사용자 정보 요청 객체
	 * @return 등록된 사용자 정보를 담은 UserResponse 객체
	 */
	public UserResponse registerUser(UserRequest userRequest) {
		// UserRequest 에서 받은 데이터로 새로운 User 객체 생성
		User user = new User(userRequest.getUserId(), userRequest.getUserPassword(), userRequest.getUserName());

		// 사용자 정보 저장
		// 사용자 정보를 데이터베이스에 저장
		userRepository.save(user);

		// UserResponse 객체로 변환 후 반환
		return UserResponse.from(user);
	}

	/**
	 * 모든 사용자 목록을 조회하는 메서드
	 *
	 * @return 모든 사용자 정보를 담은 UserResponse 객체 리스트
	 */
	public List<UserResponse> findAllUsers() {
		// 모든 사용자를 조회하여 UserResponse 리스트로 변환 후 반환
		return userRepository.findAll().stream()
			.map(UserResponse::from) // 각 User 객체를 UserResponse 로 변환
			.toList();
	}

	/**
	 * 사용자 ID로 사용자 정보를 조회하는 메서드
	 *
	 * @param id 조회할 사용자 ID
	 * @return 조회된 사용자 정보를 담은 UserResponse 객체
	 * @throws EntityNotFoundException 사용자 ID가 없을 경우 예외 발생
	 */
	public UserResponse findById(Long id) {
		// 사용자 ID로 사용자 조회, 없으면 EntityNotFoundException 발생
		User user = userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));

		// 조회된 사용자 정보를 UserResponse 로 반환
		return UserResponse.from(user);
	}

	/**
	 * 사용자 정보를 수정하는 메서드
	 *
	 * @param id 수정할 사용자 ID
	 * @param userRequest 수정할 사용자 정보
	 * @return 수정된 사용자 정보를 담은 UserResponse 객체
	 * @throws EntityNotFoundException 사용자 ID가 없을 경우 예외 발생
	 */
	public UserResponse updateUser(Long id, UserRequest userRequest) {
		// 사용자 ID로 사용자 조회, 없으면 EntityNotFoundException 발생
		User user = userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));

		// User 객체의 정보를 업데이트
		user.update(userRequest.getUserId(), userRequest.getUserName(), userRequest.getUserPassword());

		// 수정된 사용자 정보를 UserResponse 로 반환
		return UserResponse.from(user);
	}

	/**
	 * 사용자 삭제 메서드
	 *
	 * @param id 삭제할 사용자 ID
	 * @throws EntityNotFoundException 사용자 ID가 없을 경우 예외 발생
	 */
	public void deleteUser(Long id) {
		// 사용자 ID로 사용자 조회, 없으면 EntityNotFoundException 발생
		User user = userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));

		// 사용자 삭제
		userRepository.delete(user);
	}

	/**
	 * 주문을 위한 사용자 검증 메서드
	 *
	 * @param orderPendingEvent 주문 대기 이벤트 객체
	 * @throws EntityNotFoundException 사용자 ID가 없을 경우 예외 발생
	 */
	public void validateUserForOrder(OrderPendingEvent orderPendingEvent) {
		// 주문 대기 이벤트에서 사용자 ID를 추출하여 사용자 조회
		User user = userRepository.findById(orderPendingEvent.getUserSeq())
			.orElseThrow(
				() -> new EntityNotFoundException("User with ID " + orderPendingEvent.getUserSeq() + " not found"));

		// 사용자 승인 이벤트 발송
		userEventProducer.sendUserApprovedForOrderEvent(
			UserApprovedForOrderEvent.from(orderPendingEvent.getOrderId(), user));
	}
}
