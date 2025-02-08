package ecommerce.authService.client.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

	private Long userSeq;
	private String userId;
	private String userName;
}
