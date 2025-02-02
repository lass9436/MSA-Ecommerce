package ecommerce.userService.user.service;

import ecommerce.userService.messaging.event.OrderPendingEvent;
import ecommerce.userService.messaging.event.UserApprovedForOrderEvent;
import ecommerce.userService.messaging.producer.UserEventProducer;
import ecommerce.userService.user.domain.User;
import ecommerce.userService.exception.EntityNotFoundException;
import ecommerce.userService.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final UserEventProducer userEventProducer;

	public User registerUser(User user) {
		return userRepository.save(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
	}

	public User updateUser(Long id, User updatedUser) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
		user.update(updatedUser);
		return user;
	}

	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User with ID " + id + " not found");
		}
		userRepository.deleteById(id);
	}

	public void validateUserForOrder(OrderPendingEvent orderPendingEvent) {
		User user = userRepository.findById(orderPendingEvent.getUserSeq())
			.orElseThrow(
				() -> new EntityNotFoundException("User with ID " + orderPendingEvent.getUserSeq() + " not found"));
		userEventProducer.sendUserApprovedForOrderEvent(UserApprovedForOrderEvent.from(orderPendingEvent.getOrderId(), user));
	}
}

