package com.app.domain.configs.value_objects;

import com.app.domain.configs.exception.DiscountException;

import java.math.BigDecimal;

public class Discount {
    private BigDecimal value;

    public Discount() {
        this.value = BigDecimal.ZERO;
    }

    public Discount(String value) {
        this.value = init(value);
    }

    public Discount(Discount money) {
        this.value = money.value;
    }

    private Discount(BigDecimal value) {
        this.value = value;
    }

    public Discount add(String value) {
        return new Discount(this.value.add(init(value)));
    }

    public Discount add(Discount money) {
        return new Discount(this.value.add(money.value));
    }

    public Discount reverse() {
        return new Discount(BigDecimal.ONE.subtract(value));
    }

    private BigDecimal init(String value) {
        if (value == null || !value.matches("(\\d+\\.)\\d+")) {
            throw new DiscountException("Discount value is not correct");
        }
        var decimalValue =  new BigDecimal(value);
        if (decimalValue.compareTo(BigDecimal.ZERO) < 0 || decimalValue.compareTo(BigDecimal.ONE) > 0) {
            throw new DiscountException("Discount value is out of range");
        }

        return decimalValue;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
