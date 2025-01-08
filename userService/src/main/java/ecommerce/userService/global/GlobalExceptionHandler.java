package ecommerce.userService.global;


import static ecommerce.userService.exception.ErrorCodes.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ecommerce.userService.exception.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ApiResult<Void> handleEntityNotFoundException(EntityNotFoundException ex) {
		return ApiResult.failure(ENTITY_NOT_FOUND, ex.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult<Void> handleValidationException(MethodArgumentNotValidException ex) {
		List<String> errorDetails = ex.getBindingResult().getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.toList());
		return ApiResult.failure(VALIDATION_ERROR, String.join(", ", errorDetails));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ApiResult<Void> handleGlobalException(Exception ex) {
		return ApiResult.failure(INTERNAL_SERVER_ERROR, ex.getMessage());
	}
}
