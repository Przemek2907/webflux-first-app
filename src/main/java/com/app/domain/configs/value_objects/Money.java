package com.app.domain.configs.value_objects;

import com.app.domain.configs.exception.MoneyException;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

// value objects w DDD to klasa ktorej obiekty sa immutable
// i zadnaiem tej klasy jest opisanie pewnego zagadnienia
// w wygodny dla nas sposob, np. pieniadze, znizki,
// data, prawdopodobienstwo,...
@EqualsAndHashCode
public class Money {
    private BigDecimal value;

    public Money() {
        this.value = BigDecimal.ZERO;
    }

    public Money(String value) {
        this.value = init(value);
    }

    public Money(Money money) {
        this.value = money.value;
    }

    private Money(BigDecimal value) {
        this.value = value;
    }

    public Money add(String value) {
        if (value == null || !value.matches("(\\d+\\.)?\\d+")) {
            throw new MoneyException("value to add is not correct");
        }
        return new Money(this.value.add(new BigDecimal(value)));
    }

    public Money add(Money money) {
        return new Money(this.value.add(money.value));
    }

    public Money multiply(String value) {
        return new Money(this.value.multiply(init(value)).setScale(2, RoundingMode.HALF_UP));
    }

    public Money multiply(Integer value) {
        return new Money(this.value.multiply(BigDecimal.valueOf(value)).setScale(2, RoundingMode.HALF_UP));
    }

    private BigDecimal init(String value) {
        if (value == null || !value.matches("(\\d+\\.)?\\d+")) {
            throw new MoneyException("money value is not correct");
        }
        return new BigDecimal(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
