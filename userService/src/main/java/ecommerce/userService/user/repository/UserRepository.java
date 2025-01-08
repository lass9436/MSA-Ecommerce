package ecommerce.userService.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ecommerce.userService.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

