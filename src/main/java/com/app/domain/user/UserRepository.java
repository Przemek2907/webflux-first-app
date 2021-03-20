package com.app.domain.user;

import com.app.domain.configs.repository.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Flux<User> findAll();
    Mono<User> findByLogin(String login);
}
