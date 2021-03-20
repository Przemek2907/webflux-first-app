package com.app.domain.product;

import com.app.domain.product.dto.CreateProductResponseDto;
import com.app.domain.product.dto.GetProductDto;
import com.app.domain.configs.value_objects.Discount;
import com.app.domain.configs.value_objects.Money;
import com.app.infrastructure.repository.entity.ProductDocument;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Product {

    String id;

    String name;
    Money price;
    Discount discount;

    public Money totalPrice() {
        return price.multiply(discount.reverse().toString());
    }

    public GetProductDto toGetProductDto() {
        return GetProductDto
                .builder()
                .id(id)
                .name(name)
                .totalPrice(totalPrice().toString())
                .build();
    }

    public CreateProductResponseDto toCreateProductResponseDto() {
        return CreateProductResponseDto
                .builder()
                .id(id)
                .name(name)
                .build();
    }

    public ProductDocument toDocument() {
        return ProductDocument
                .builder()
                .id(id)
                .name(name)
                .price(price)
                .discount(discount)
                .build();
    }

}
