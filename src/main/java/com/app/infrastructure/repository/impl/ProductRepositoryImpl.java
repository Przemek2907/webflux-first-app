package com.app.infrastructure.repository.impl;

import com.app.domain.product.Product;
import com.app.domain.product.ProductRepository;
import com.app.infrastructure.repository.entity.ProductDocument;
import com.app.infrastructure.repository.mongo.MongoProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final MongoProductRepository mongoProductRepository;

    @Override
    public Mono<Product> save(Product product) {
        return mongoProductRepository
                .save(product.toDocument())
                .map(ProductDocument::toProduct);
    }

    @Override
    public Flux<Product> saveAll(List<Product> products) {
        return mongoProductRepository
                .saveAll(products.stream().map(Product::toDocument).collect(Collectors.toList()))
                .map(ProductDocument::toProduct);
    }

    @Override
    public Mono<Product> findById(String id) {
        return mongoProductRepository
                .findById(id)
                .map(ProductDocument::toProduct);
    }

    @Override
    public Flux<Product> findAllById(List<String> listOfIds) {
        return mongoProductRepository.findAllById(listOfIds)
                .map(ProductDocument::toProduct);
    }

    @Override
    public Mono<Product> deleteById(String id) {
        return mongoProductRepository
                .findById(id)
                .flatMap(productDocument -> {
                    mongoProductRepository.delete(productDocument);
                            return Mono.just(productDocument.toProduct());
                        }
                );
    }

    @Override
    public Flux<Product> deleteAllById(List<String> ids) {
        return mongoProductRepository
                .findAllById(ids)
                .collectList()
                .flatMapMany(orderDocuments -> {
                    mongoProductRepository.deleteAll(orderDocuments);
                            return Flux
                                    .fromIterable(orderDocuments
                                            .stream()
                                            .map(ProductDocument::toProduct)
                                            .collect(Collectors.toList())
                                    );
                        }

                );

    }

    @Override
    public Flux<Product> findAll() {
        return mongoProductRepository
                .findAll()
                .map(ProductDocument::toProduct);
    }
}
