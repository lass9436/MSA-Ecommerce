package ecommerce.productService.product.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {

	@NotBlank(message = "상품 이름은 비어 있을 수 없습니다.")
	@Size(min = 2, max = 50, message = "상품 이름은 2자 이상 50자 이하이어야 합니다.")
	private String productName;

	@NotBlank(message = "상품 카테고리는 비어 있을 수 없습니다.")
	@Size(min = 2, max = 50, message = "상품 카테고리는 2자 이상 50자 이하이어야 합니다.")
	private String productCategory;

	@NotNull(message = "상품 수량은 비어 있을 수 없습니다.")
	private Integer productQuantity;

	@NotNull(message = "상품 가격은 비어 있을 수 없습니다.")
	private Long productPrice;

	@NotNull(message = "상점 ID는 비어 있을 수 없습니다.")
	private Long storeId;
}
