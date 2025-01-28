package com.example.filter;

import com.example.client.AuthClient;
import com.example.exception.UnauthorizedAccessException;
import com.example.routes.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private AuthClient authClient;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isEnabled()) {
                if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
                    throw new UnauthorizedAccessException("Authentication Failed!", "Authorization header is not present");
                }
                String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (token == null || !token.startsWith("Bearer ")) {
                    throw new UnauthorizedAccessException("Authentication Failed!", "Token is not correct format");
                } else {
                    token = token.substring(7);

                    return authClient.validateToken(token)
                            .flatMap(isValid -> {
                                if (isValid.status()) {
                                    return chain.filter(exchange);
                                } else {
                                    throw new UnauthorizedAccessException("Authentication Failed!", "Invalid token");
                                }
                            });
                }
            } else {
                return chain.filter(exchange);
            }
        };
    }

    public static class Config {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public Config(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
