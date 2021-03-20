package com.app.domain.order;

import com.app.domain.configs.repository.CrudRepository;
import com.app.domain.product.Product;
import reactor.core.publisher.Flux;

public interface OrderRepository extends CrudRepository<Order, String> {

    Flux<Order> findAll();
}
