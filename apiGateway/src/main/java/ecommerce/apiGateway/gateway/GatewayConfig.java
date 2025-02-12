package ecommerce.apiGateway.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			// Auth-Service
			.route("auth-service", r -> r.path("/api/auth/**")
				.uri("http://localhost:9060/"))

			// User-Service
			.route("user-service", r -> r.path("/api/users/**")
				.uri("http://localhost:9030/"))

			// Order-Service
			.route("order-service", r -> r.path("/api/orders/**")
				.uri("http://localhost:9040/"))

			// Product-Service (Products Endpoint)
			.route("product-service-products", r -> r.path("/api/products/**")
				.uri("http://localhost:9010/"))

			// Product-Service (Stores Endpoint)
			.route("product-service-stores", r -> r.path("/api/stores/**")
				.uri("http://localhost:9010/"))

			// Seller-Service
			.route("seller-service", r -> r.path("/api/sellers/**")
				.uri("http://localhost:9020/"))

			.build();
	}
}
