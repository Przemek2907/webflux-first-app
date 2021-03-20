package com.app.infrastructure.repository.impl;

import com.app.domain.order.Order;
import com.app.domain.order.OrderRepository;
import com.app.domain.product.Product;
import com.app.infrastructure.repository.entity.OrderDocument;
import com.app.infrastructure.repository.entity.ProductDocument;
import com.app.infrastructure.repository.mongo.MongoOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final MongoOrderRepository mongoOrderRepository;

    @Override
    public Mono<Order> save(Order item) {
        return mongoOrderRepository
                .save(item.toDocument())
                .map(OrderDocument::toOrder);
    }

    @Override
    public Flux<Order> saveAll(List<Order> items) {
        return mongoOrderRepository.saveAll(items.stream()
                .map(Order::toDocument)
                .collect(Collectors.toList()))
                .map(OrderDocument::toOrder);
    }

    @Override
    public Mono<Order> findById(String documentId) {
        return mongoOrderRepository.findById(documentId)
                .map(OrderDocument::toOrder);
    }

    @Override
    public Flux<Order> findAllById(List<String> listOfIds) {
        return mongoOrderRepository.findAllById(listOfIds)
                .map(OrderDocument::toOrder);
    }

    @Override
    public Mono<Order> deleteById(String id) {
        return mongoOrderRepository
                .findById(id)
                .flatMap(orderDocument -> {
                            mongoOrderRepository.delete(orderDocument);
                            return Mono.just(orderDocument.toOrder());
                        }
                );
    }

    @Override
    public Flux<Order> deleteAllById(List<String> ids) {
        return mongoOrderRepository
                .findAllById(ids)
                .collectList()
                .flatMapMany(orderDocuments -> {
                            mongoOrderRepository.deleteAll(orderDocuments);
                            return Flux
                                    .fromIterable(orderDocuments
                                            .stream()
                                            .map(OrderDocument::toOrder)
                                            .collect(Collectors.toList())
                                    );
                        }

                );
    }

    @Override
    public Flux<Order> findAll() {
        return mongoOrderRepository
                .findAll()
                .map(OrderDocument::toOrder);
    }
}
