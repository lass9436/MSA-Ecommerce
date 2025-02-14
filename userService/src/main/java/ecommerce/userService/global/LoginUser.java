package ecommerce.userService.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginUser {
	private String userId;
	private Long userSeq;
	private String role;
}
