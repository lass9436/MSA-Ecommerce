package ecommerce.payService.global;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoginUserArgumentResolver loginUserArgumentResolver;

	public WebMvcConfig(LoginUserArgumentResolver loginUserArgumentResolver) {
		this.loginUserArgumentResolver = loginUserArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// 사용자 정의 Argument Resolver 등록
		resolvers.add(loginUserArgumentResolver);
	}
}
