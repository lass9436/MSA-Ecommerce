package ecommerce.userService.user.service;

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
}

