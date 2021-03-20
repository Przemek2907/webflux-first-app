package com.app.infrastructure.repository.mongo;


import com.app.infrastructure.repository.entity.UserDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface MongoUserRepository extends ReactiveMongoRepository<UserDocument, String> {
    Mono<UserDocument> findByLogin(String login);
}
