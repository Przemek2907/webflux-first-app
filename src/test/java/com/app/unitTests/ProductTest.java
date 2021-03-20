package com.app.unitTests;

import com.app.domain.product.Product;
import com.app.domain.configs.value_objects.Discount;
import com.app.domain.configs.value_objects.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    @DisplayName("Test total price with discount applied")
    public void shouldReturnLoweredTotalPrice_WhenDiscountApplied() {
        Money money = new Money("112.00");
        Discount discount = new Discount("0.30");


        Product product = Product.builder()
                .id("1")
                .name("PRODUCT 1")
                .price(money)
                .discount(discount)
                .build();

        Assertions.assertEquals(new Money("78.40"), product.totalPrice());
    }

}
