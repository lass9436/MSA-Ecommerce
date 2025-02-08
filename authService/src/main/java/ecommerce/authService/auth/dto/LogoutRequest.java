package ecommerce.authService.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
	private String refreshToken;
}
