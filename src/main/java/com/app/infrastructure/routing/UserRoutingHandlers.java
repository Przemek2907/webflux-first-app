package com.app.infrastructure.routing;

import com.app.application.service.RegisterUserService;
import com.app.domain.order.dto.CreateOrderDto;
import com.app.domain.user.dto.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@Component
public class UserRoutingHandlers {

    private final RegisterUserService registerUserService;

    public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
        Mono<RegisterUserDto> registerUserRequest = serverRequest.bodyToMono(RegisterUserDto.class);
        return RoutingHandlersUtil.toServerResponse(registerUserService.registerUser(registerUserRequest), HttpStatus.CREATED);
    }


}
