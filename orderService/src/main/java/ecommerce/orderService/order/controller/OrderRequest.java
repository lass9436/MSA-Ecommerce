package ecommerce.orderService.order.controller;

import java.util.List;

import ecommerce.orderService.order.domain.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

	@NotNull(message = "유저 시퀀스는 비어 있을 수 없습니다.")
	private Long userSeq;

	@NotNull(message = "주문 금액은 비어 있을 수 없습니다.")
	@Min(value = 1, message = "주문 금액은 1 이상이어야 합니다.")
	private Long orderAmount;

	@NotNull(message = "주문 상태는 비어 있을 수 없습니다.")
	private OrderStatus orderStatus;

	@NotEmpty(message = "주문 상품 리스트는 비어 있을 수 없습니다.")
	@Valid
	private List<OrderProductRequest> orderProductRequests;
}
