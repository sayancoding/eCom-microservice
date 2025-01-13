package com.example.filter;

import com.example.client.AuthClient;
import com.example.dto.TokenValidationResponse;
import com.example.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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
                String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (token == null || !token.startsWith("Bearer ")) {
//                    return Mono.just(exchange).then(chain.filter(exchange).then(Mono.defer(() -> {
//                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                        return exchange.getResponse().setComplete();
//                    })));

                    return Mono.error(new UnauthorizedAccessException("Authentication required."));
                }
                else {
                    token = token.substring(7);

                    return authClient.validateToken(token)
                            .flatMap(isValid -> {
                                if (isValid.status()) {
                                    return chain.filter(exchange);
                                } else {
                                    throw new UnauthorizedAccessException("Authentication Failed!");
//                                    return Mono.error(new UnauthorizedAccessException("Authentication Failed!"));
//                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                                    return exchange.getResponse().setComplete();
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
