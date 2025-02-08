package ecommerce.authService.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	@NotBlank(message = "유저 ID는 비어 있을 수 없습니다.")
	@Size(min = 6, max = 50, message = "유저 ID는 6자 이상 50자 이하이어야 합니다.")
	private String userId;

	@NotBlank(message = "유저 비밀번호는 비어 있을 수 없습니다.")
	@Size(min = 6, max = 50, message = "유저 비밀번호는 6자 이상 50자 이하이어야 합니다.")
	private String userPassword;
}
