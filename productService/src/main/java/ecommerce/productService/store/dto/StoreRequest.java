package ecommerce.productService.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreRequest {

	@NotBlank(message = "상점 이름은 비어 있을 수 없습니다.")
	@Size(min = 2, max = 50, message = "상점 이름은 2자 이상 50자 이하이어야 합니다.")
	private String storeName;

	@NotBlank(message = "상점 계좌 번호는 비어 있을 수 없습니다.")
	@Size(min = 10, max = 50, message = "상점 계좌 번호는 10자 이상 50자 이하이어야 합니다.")
	private String storeAccountNumber;

	@NotBlank(message = "상점 사업자 등록번호는 비어 있을 수 없습니다.")
	@Size(min = 10, max = 50, message = "상점 사업자 등록번호는 10자 이상 50자 이하이어야 합니다.")
	private String storeLicense;

	@NotNull(message = "판매자 시퀀스는 비어 있을 수 없습니다.")
	private Long sellerSeq;
}
