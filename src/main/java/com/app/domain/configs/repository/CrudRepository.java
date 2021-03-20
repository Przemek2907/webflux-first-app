package com.app.domain.configs.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CrudRepository<T, ID> {
    Mono<T> save(T item);
    Flux<T> saveAll(List<T> items);
    Mono<T> findById(ID id);
    Flux<T> findAllById(List<ID> ids);
    Mono<T> deleteById(ID id);
    Flux<T> deleteAllById(List<ID> ids);
}
