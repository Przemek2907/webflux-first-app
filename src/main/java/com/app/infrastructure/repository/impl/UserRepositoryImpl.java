package com.app.infrastructure.repository.impl;

import com.app.domain.user.User;
import com.app.domain.user.UserRepository;
import com.app.infrastructure.repository.entity.ProductDocument;
import com.app.infrastructure.repository.entity.UserDocument;
import com.app.infrastructure.repository.mongo.MongoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final MongoUserRepository userRepository;

    @Override
    public Mono<User> save(User user) {
        return userRepository
                .save(user.toUserDocument())
                .map(UserDocument::toUser);
    }

    @Override
    public Flux<User> saveAll(List<User> users) {
        return userRepository
                .saveAll(users.stream().map(User::toUserDocument).collect(Collectors.toList()))
                .map(UserDocument::toUser);
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .map(UserDocument::toUser);
    }

    @Override
    public Flux<User> findAllById(List<String> ids) {
        return userRepository.findAllById(ids)
                .map(UserDocument::toUser);
    }

    @Override
    public Mono<User> deleteById(String id) {
        return userRepository.findById(id)
                .flatMap(
                        userDocument -> {
                            userRepository.delete(userDocument);
                            return Mono.just(userDocument.toUser());
                        }
                );
    }

    @Override
    public Flux<User> deleteAllById(List<String> ids) {
        return userRepository
                .findAllById(ids)
                .collectList()
                .flatMapMany(userDocuments -> {
                    userRepository.deleteAll(userDocuments);
                            return Flux
                                    .fromIterable(userDocuments
                                            .stream()
                                            .map(UserDocument::toUser)
                                            .collect(Collectors.toList())
                                    );
                        }

                );
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll()
                .map(UserDocument::toUser);
    }

    @Override
    public Mono<User> findByLogin(String username) {
        return
                userRepository.findByLogin(username)
                .map(UserDocument::toUser);
    }
}
