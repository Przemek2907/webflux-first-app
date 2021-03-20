package com.app.domain.product;

import com.app.domain.configs.repository.CrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends CrudRepository<Product, String> {

    Flux<Product> findAll();
}
