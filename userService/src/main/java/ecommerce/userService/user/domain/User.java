package ecommerce.userService.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`user`")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq")
	private Long userSeq;

	@Column(name = "user_id", nullable = false, length = 50)
	private String userId;

	@Column(name = "user_password", nullable = false, length = 50)
	private String userPassword;

	@Column(name = "user_name", nullable = false, length = 50)
	private String userName;

}
