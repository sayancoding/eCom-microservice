package com.example.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Router {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("PRODUCT-SERVICE", r -> r.path("/api/product/**")
                        .uri("lb://product-service"))

                .route("INVENTORY-SERVICE", r -> r.path("/api/inventory/**")
                        .uri("lb://inventory-service"))

                .route("ORDER-SERVICE", r -> r.path("/api/order/**")
                        .uri("lb://order-service"))
                .build();
    }

}
