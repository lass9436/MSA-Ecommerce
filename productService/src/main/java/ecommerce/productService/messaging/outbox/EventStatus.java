package ecommerce.productService.messaging.outbox;

public enum EventStatus {
	SUCCESS,  // 처리 성공
	PENDING,  // 처리 대기 중
	FAILURE   // 처리 실패
}
