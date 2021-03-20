package com.app.application.service;

import com.app.domain.configs.validator.generic.Validator;
import com.app.domain.configs.validator.generic.ValidatorException;
import com.app.domain.order.dto.validator.CreateOrderDtoValidator;
import com.app.domain.product.dto.CreateProductDto;
import com.app.domain.product.dto.CreateProductResponseDto;
import com.app.application.exception.ProductsServiceException;
import com.app.domain.product.dto.GetProductDto;
import com.app.domain.product.dto.validator.CreateProductDtoValidator;
import com.app.domain.product.Product;
import com.app.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductRepository productRepository;

    //TODO napisac testy do serwisów

    @Transactional
    public Mono<CreateProductResponseDto> create(Mono<CreateProductDto> createProductDtoMono) {
        if (createProductDtoMono == null) {
            return Mono.error(() -> new ProductsServiceException("Create product object data is null"));
        }
        return createProductDtoMono
                .flatMap(createProductDto -> {
//                    var createProductDtoValidator = new CreateProductDtoValidator();
//                    var errors = createProductDtoValidator.validate(createProductDto);
//                    if (!errors.isEmpty()) {
//                        return Mono.error(() -> new ProductsServiceException("Validation error: " + errors
//                                .entrySet()
//                                .stream()
//                                .map(e -> e.getKey() + ": " + e.getValue())));
//                    }

                    Validator.validate(new CreateProductDtoValidator(), createProductDto);


                    var product = createProductDto.toProduct();
                    return productRepository
                            .save(product)
                            .map(Product::toCreateProductResponseDto);
                });
    }

    public Flux<GetProductDto> findAll() {
        return productRepository
                .findAll()
                .map(Product::toGetProductDto);
    }

    //TODO czy tutaj powinniśmy zwracać błąd czy tak jak teraz pustą odpowiedź z kodem HTTP 200
    public Mono<GetProductDto> findById(String id) {
        return productRepository
                .findById(id)
                .map(Product::toGetProductDto);
    }


}
