package ecommerce.orderService.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResult<T> {

	private boolean success;
	private T data;
	private String errorCode;
	private String errorMessage;

	public static <T> ApiResult<T> success(T data) {
		return new ApiResult<>(true, data, null, null);
	}

	public static <T> ApiResult<T> failure(String errorCode, String errorMessage) {
		return new ApiResult<>(false, null, errorCode, errorMessage);
	}
}

