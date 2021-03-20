package com.app.application.service;

import com.app.application.exception.OrdersServiceException;
import com.app.application.exception.ProductsServiceException;
import com.app.domain.configs.validator.generic.Validator;
import com.app.domain.configs.validator.generic.ValidatorException;
import com.app.domain.order.Order;
import com.app.domain.order.OrderRepository;
import com.app.domain.order.dto.CreateOrderDto;
import com.app.domain.order.dto.CreateOrderResponseDto;
import com.app.domain.order.dto.GetOrderDto;
import com.app.domain.order.dto.ProductWithQuantityDto;
import com.app.domain.order.dto.validator.CreateOrderDtoValidator;
import com.app.domain.order_position.OrderPosition;
import com.app.domain.product.Product;
import com.app.domain.product.ProductRepository;
import com.app.domain.product.dto.GetProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    public Mono<CreateOrderResponseDto> placeOrder(Mono<CreateOrderDto> orderRequest) {

        if (orderRequest == null) {
            return Mono.error(() -> new OrdersServiceException("Placing order has failed. Object is null"));
        }

        return orderRequest
                .flatMap(
                        createOrderDto -> {
                         Validator.validate(new CreateOrderDtoValidator(), createOrderDto);

                            var productIds = createOrderDto
                                    .getProductWithQuantities()
                                    .stream()
                                    .map(ProductWithQuantityDto::getId)
                                    .collect(Collectors.toList());

                            var quantities = createOrderDto
                                    .getProductWithQuantities()
                                    .stream()
                                    .map(ProductWithQuantityDto::getQuantity)
                                    .collect(Collectors.toList());

                            return productRepository
                            .findAllById(productIds)
                            .collectList()
                            .flatMap(products -> {
                                if (productIds.size() != products.size()) {
                                    return Mono.error(() -> new OrdersServiceException("Cannot get all products"));
                                }
                                var orderPositions = IntStream
                                        .range(0, products.size())
                                        .boxed()
                                        .map(index -> OrderPosition
                                                .builder()
                                                .product(products.get(index))
                                                .quantity(quantities.get(index))
                                                .build())
                                        .collect(Collectors.toList());
                                return orderRepository
                                        .save(Order
                                                .builder()
                                                .orderTimeStamp(LocalDateTime.now())
                                                .orderPositions(orderPositions)
                                                .build())
                                        .map(Order::toCreateOrderResponseDto);
                            });
                        }
                );

    }


    public Flux<GetOrderDto> findAll() {
        return orderRepository
                .findAll()
                .map(Order::toGetOrderDto);
    }

    public Mono<GetOrderDto> findById(String id) {
        return orderRepository
                .findById(id)
                .map(Order::toGetOrderDto);
    }

}
