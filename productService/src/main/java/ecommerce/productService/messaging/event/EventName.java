package ecommerce.productService.messaging.event;

public final class EventName { // 상수 전용 클래스로 선언, 외부에서 상속 불가
	public static final String ORDER_RESERVE_PRODUCT = "ORDER_RESERVE_PRODUCT";
	public static final String PRODUCT_RESERVED_FOR_ORDER = "PRODUCT_RESERVED_FOR_ORDER";
	public static final String PRODUCT_RESERVATION_FAILED_FOR_ORDER = "PRODUCT_RESERVATION_FAILED_FOR_ORDER";

	// 개인 생성자 방지 (객체 생성 불가)
	private EventName() {
	}
}
