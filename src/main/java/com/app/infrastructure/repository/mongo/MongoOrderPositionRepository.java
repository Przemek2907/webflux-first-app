package com.app.infrastructure.repository.mongo;

import com.app.domain.order_position.OrderPosition;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoOrderPositionRepository extends ReactiveMongoRepository<OrderPosition, String> {
}
