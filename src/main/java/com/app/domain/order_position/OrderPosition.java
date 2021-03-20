package com.app.domain.order_position;

import com.app.domain.product.Product;
import com.app.domain.configs.value_objects.Money;
import lombok.*;

// potrzebuje akcesorów
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPosition {

    String id;
    Product product;
    Integer quantity;

    public Money totalPrice() {
        return product.totalPrice().multiply(quantity);
    }


}
