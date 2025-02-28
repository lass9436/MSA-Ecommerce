package ecommerce.payService.global;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 매개변수가 @Login 어노테이션을 가지고 있고, LoginUser 타입인지 확인
		return parameter.getParameterAnnotation(Login.class) != null &&
			LoginUser.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) throws Exception {
		// 헤더에서 "X-User-Id", "X-User-Role" 값 가져오기
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		String userId = request.getHeader("X-User-Id");
		String role = request.getHeader("X-User-Role");
		Long userSeq = Long.valueOf(request.getHeader("X-User-Seq"));

		if (userId == null || role == null) {
			throw new IllegalArgumentException("Required headers are missing: X-User-Id or X-User-Role");
		}

		// LoginUser 객체 생성
		return new LoginUser(userId, userSeq, role);
	}
}
