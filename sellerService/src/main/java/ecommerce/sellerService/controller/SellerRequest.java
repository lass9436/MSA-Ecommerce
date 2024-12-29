package ecommerce.sellerService.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellerRequest {

	@NotBlank(message = "판매자 ID는 비어 있을 수 없습니다.")
	@Size(min = 6, max = 50, message = "판매자 ID는 6자 이상 50자 이하이어야 합니다.")
	private String sellerId;

	@NotBlank(message = "판매자 비밀번호는 비어 있을 수 없습니다.")
	@Size(min = 6, max = 50, message = "판매자 비밀번호는 6자 이상 50자 이하이어야 합니다.")
	private String sellerPassword;

	@NotBlank(message = "판매자 이름은 비어 있을 수 없습니다.")
	@Size(min = 2, max = 50, message = "판매자 이름은 2자 이상 50자 이하이어야 합니다.")
	private String sellerName;
}
