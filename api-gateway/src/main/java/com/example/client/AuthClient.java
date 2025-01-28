package com.example.client;

import com.example.dto.ErrorResponse;
import com.example.dto.TokenValidationResponse;
import com.example.exception.UnauthorizedAccessException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class AuthClient {

    private String AUTH_BASE_URL = "http://auth-service/api/auth";

    @Autowired
    private WebClient.Builder webclienBuilder;

    public Mono<TokenValidationResponse> validateToken(String token){

        WebClient webClient= getWebClient();
        return webClient.get().uri("/validate?token="+token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        res -> res.bodyToMono(ErrorResponse.class)
                                .flatMap(response -> Mono.error(new UnauthorizedAccessException(response.errorMessage(), response.cause())))
                )
                .bodyToMono(TokenValidationResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> Mono.just(new TokenValidationResponse(false)));

    }
    private WebClient getWebClient(){
        return webclienBuilder
                .baseUrl(AUTH_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
