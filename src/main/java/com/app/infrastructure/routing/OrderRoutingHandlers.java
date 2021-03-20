package com.app.infrastructure.routing;

import com.app.application.service.OrderService;
import com.app.domain.order.dto.CreateOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderRoutingHandlers {

    private final OrderService orderService;

    public Mono<ServerResponse> createOrder(ServerRequest serverRequest) {
        Mono<CreateOrderDto> orderRequest = serverRequest.bodyToMono(CreateOrderDto.class);
        return RoutingHandlersUtil.toServerResponse(orderService.placeOrder(orderRequest), HttpStatus.CREATED);
    }

    public Mono<ServerResponse> getAllOrders(ServerRequest serverRequest) {
        return RoutingHandlersUtil.toServerResponse(orderService.findAll().collectList(), HttpStatus.OK);
    }

    public Mono<ServerResponse> getOneOrder(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return RoutingHandlersUtil.toServerResponse(orderService.findById(id), HttpStatus.OK);
    }
}
