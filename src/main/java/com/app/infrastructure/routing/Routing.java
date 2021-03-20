package com.app.infrastructure.routing;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class Routing {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(
            ProductsRoutingHandlers productsRoutingHandlers,
            OrderRoutingHandlers orderRoutingHandlers,
            UserRoutingHandlers userRoutingHandlers,
            SecurityRoutingHandlers securityRoutingHandlers
    ) {
        return nest(
                path("/products"),
                route(GET("").and(accept(MediaType.APPLICATION_JSON)), productsRoutingHandlers::getAllProducts)
                        .andRoute(GET("/{id}").and(accept(MediaType.APPLICATION_JSON)), productsRoutingHandlers::getOneProduct)
                        .andRoute(POST("").and(accept(MediaType.APPLICATION_JSON)), productsRoutingHandlers::createProduct)
        ).andNest(
                path("/orders"),
                route(GET("").and(accept(MediaType.APPLICATION_JSON)), orderRoutingHandlers::getAllOrders)
                        .andRoute(GET("/{id}").and(accept(MediaType.APPLICATION_JSON)), orderRoutingHandlers::getOneOrder)
                        .andRoute(POST("").and(accept(MediaType.APPLICATION_JSON)), orderRoutingHandlers::createOrder)
        ).andNest(
                path("/users"),
                route(POST("/registration").and(accept(MediaType.APPLICATION_JSON)), userRoutingHandlers::registerUser)
        ).andNest(
                path("/login"),
                route(POST("").and(accept(MediaType.APPLICATION_JSON)), securityRoutingHandlers::login)
        );
    }
}
