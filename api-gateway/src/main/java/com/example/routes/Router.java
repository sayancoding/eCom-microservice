package com.example.routes;

import com.example.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Router {
    @Autowired
    private AuthenticationFilter authFilter;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("PRODUCT-SERVICE",
                        r -> r.path("/api/product/**")
                                .filters(f->f.filter(authFilter.apply(new AuthenticationFilter.Config(true))))
                        .uri("lb://product-service")
                )

                .route("INVENTORY-SERVICE", r -> r.path("/api/inventory/**")
                        .filters(f->f.filter(authFilter.apply(new AuthenticationFilter.Config(true))))
                        .uri("lb://inventory-service")
                )

                .route("ORDER-SERVICE", r -> r.path("/api/order/**")
                        .filters(f->f.filter(authFilter.apply(new AuthenticationFilter.Config(true))))
                        .uri("lb://order-service")
                )

                .route("AUTH-SERVICE", r -> r.path("/api/auth/**")
                        .uri("lb://auth-service")
                )

                .build();
    }

}
