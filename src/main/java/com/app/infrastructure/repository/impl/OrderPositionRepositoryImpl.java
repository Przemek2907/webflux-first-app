package com.app.infrastructure.repository.impl;

import com.app.domain.order_position.OrderPosition;
import com.app.domain.order_position.OrderPositionRepository;
import com.app.infrastructure.repository.mongo.MongoOrderPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderPositionRepositoryImpl implements OrderPositionRepository {

    private final MongoOrderPositionRepository mongoOrderPositionRepository;

    @Override
    public Mono<OrderPosition> save(OrderPosition item) {
        return null;
    }

    @Override
    public Flux<OrderPosition> saveAll(List<OrderPosition> items) {
        return null;
    }

    @Override
    public Mono<OrderPosition> findById(String s) {
        return null;
    }

    @Override
    public Flux<OrderPosition> findAllById(List<String> strings) {
        return null;
    }

    @Override
    public Mono<OrderPosition> deleteById(String s) {
        return null;
    }

    @Override
    public Flux<OrderPosition> deleteAllById(List<String> strings) {
        return null;
    }
}

