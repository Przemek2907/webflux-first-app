package com.app.infrastructure.repository.mongo;

import com.app.domain.order.Order;
import com.app.infrastructure.repository.entity.OrderDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoOrderRepository extends ReactiveMongoRepository<OrderDocument, String> {
}
