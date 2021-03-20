package com.app.infrastructure.routing;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface RoutingHandlersUtil {
    static <T> Mono<ServerResponse> toServerResponse(Mono<T> mono, HttpStatus status) {
        return mono.flatMap(products -> ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(products)))
                .onErrorResume(e -> ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(e.getMessage())));
    }
}
