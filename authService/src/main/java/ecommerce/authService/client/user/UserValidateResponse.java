package ecommerce.authService.client.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserValidateResponse {

	private Long userSeq;
	private String userId;
	private String userName;
}
