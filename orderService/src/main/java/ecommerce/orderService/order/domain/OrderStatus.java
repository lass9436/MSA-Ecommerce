package ecommerce.orderService.order.domain;

public enum OrderStatus {
	ORDER_PENDING,         // 주문 생성 요청을 받았지만, 아직 주문이 생성되지 않은 초기 상태
	ORDER_COMPLETED,       // 주문 완료
	ORDER_CANCELLED,       // 주문 취소 (결제 전)
	PAYMENT_COMPLETED,     // 결제 완료
	PAYMENT_REFUNDED,      // 결제 환불 (결제 후)

	USER_FAILED,           // 유저 검증 실패
	PRODUCT_FAILED,        // 상품 검증 실패 (재고 부족, 가격 불일치 등)
}
