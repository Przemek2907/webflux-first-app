package com.app.infrastructure.routing;

import com.app.application.service.ProductsService;
import com.app.domain.product.Product;
import com.app.domain.product.dto.CreateProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductsRoutingHandlers {

    private final ProductsService productsService;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        var body = serverRequest.bodyToMono(CreateProductDto.class);
        return RoutingHandlersUtil.toServerResponse(productsService.create(body), HttpStatus.CREATED);
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest serverRequest) {
        return RoutingHandlersUtil.toServerResponse(productsService.findAll().collectList(), HttpStatus.OK);
    }

    public Mono<ServerResponse> getOneProduct(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return RoutingHandlersUtil.toServerResponse(productsService.findById(id), HttpStatus.OK);
    }


}
