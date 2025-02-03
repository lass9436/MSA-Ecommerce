package ecommerce.productService.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.okhttp.OkHttpClient;

@Configuration
public class FeignConfig {

	@Bean
	public Feign.Builder feignBuilder() {
		return Feign.builder()
			.client(new OkHttpClient());
	}
}
