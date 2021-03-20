package com.app.infrastructure.repository.entity;

import com.app.domain.configs.value_objects.Discount;
import com.app.domain.configs.value_objects.Money;
import com.app.domain.product.Product;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Document(collection = "products")
public class ProductDocument {

    @Id
    String id;

    String name;
    Money price;
    Discount discount;

    public Product toProduct() {
        return Product
                .builder()
                .id(id)
                .name(name)
                .price(price)
                .discount(discount)
                .build();
    }
}
