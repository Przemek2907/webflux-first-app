package com.app.infrastructure.repository.mongo;

import com.app.domain.product.Product;
import com.app.infrastructure.repository.entity.ProductDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoProductRepository extends ReactiveMongoRepository<ProductDocument, String> {
}
