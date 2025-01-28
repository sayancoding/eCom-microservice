package com.example.routes;

import com.example.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Router {
    @Autowired
    private AuthenticationFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTH-SERVICE", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .circuitBreaker(cb -> cb.setName("authServiceDown").setFallbackUri("forward:/fallback/auth"))
                        ).uri("lb://auth-service"))

                .route("PRODUCT-SERVICE", r -> r
                        .path("/api/product/**")
                        .filters(f -> f
                                .filter(authFilter.apply(new AuthenticationFilter.Config(true)))
                                .circuitBreaker(cb -> cb.setName("productServiceDown").setFallbackUri("forward:/fallback/product"))
                        ).uri("lb://product-service")
                )

                .route("INVENTORY-SERVICE", r -> r
                        .path("/api/inventory/**")
                        .filters(f -> f
                                .filter(authFilter.apply(new AuthenticationFilter.Config(true)))
                                .circuitBreaker(cb -> cb.setName("inventoryServiceDown").setFallbackUri("forward:/fallback/inventory"))
                        ).uri("lb://inventory-service"))

                .route("ORDER-SERVICE", r -> r
                        .path("/api/order/**")
                        .filters(f -> f
                                .filter(authFilter.apply(new AuthenticationFilter.Config(true)))
                                .circuitBreaker(cb -> cb.setName("orderServiceDown").setFallbackUri("forward:/fallback/order"))
                        ).uri("lb://order-service"))

                .build();
    }

}
