package com.app.domain.product.dto;

import com.app.domain.product.Product;
import com.app.domain.configs.value_objects.Discount;
import com.app.domain.configs.value_objects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    private String name;
    private String price;
    private String discount;

    public Product toProduct() {
        return Product
                .builder()
                .name(name)
                .price(new Money(price))
                .discount(new Discount(discount))
                .build();
    }
}
