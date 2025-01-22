package ecommerce.orderService.order.domain;

public enum OrderStatus {
	ORDER_CREATED,         // 주문 생성
	ORDER_CANCELLED,       // 주문 취소 (결제 전)
	PAYMENT_COMPLETED,     // 결제 완료
	PAYMENT_REFUNDED,      // 결제 환불 (결제 후)
}
