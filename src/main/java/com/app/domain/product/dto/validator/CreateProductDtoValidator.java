package com.app.domain.product.dto.validator;

import com.app.domain.product.dto.CreateProductDto;
import com.app.domain.configs.validator.generic.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CreateProductDtoValidator implements Validator<CreateProductDto> {

    private static final String NULL_OBJECT = "NO_OBJECT_PROVIDED";
    private static final String PRODUCT_NAME = "PRODUCT_NAME";
    private static final String PRODUCT_PRICE = "PRICE";
    private static final String PRODUCT_DISCOUNT = "DISCOUNT";
    private static final Set<String> ALLOWED_CURRENCIES = Set.of("PLN", "EUR", "USD");


    @Override
    public Map<String, String> validate(CreateProductDto createProductDto) {
        var errors = new HashMap<String, String>();
        // TODO continue

        if (createProductDto == null) {
            appendErrorMessage(errors, NULL_OBJECT, "Object is null");
        }

        if (createProductDto.getName() == null || createProductDto.getName().trim().length() == 0) {
            appendErrorMessage(errors, PRODUCT_NAME, "Product name is missing");
        }

        if (createProductDto.getName() != null && createProductDto.getName().length() < 2) {
            appendErrorMessage(errors, PRODUCT_NAME, "Product name is too short. Minimum 2 characters required");
        }

        if (createProductDto.getPrice() == null) {
            appendErrorMessage(errors, PRODUCT_PRICE, "Price is missing");
        }

        if (createProductDto.getPrice() != null && new BigDecimal(createProductDto.getPrice()).compareTo(BigDecimal.ZERO) <= 0) {
            appendErrorMessage(errors, PRODUCT_PRICE, "Price of the product has to be greater than 0");
        }

        if (createProductDto.getDiscount() != null && (new BigDecimal(createProductDto.getDiscount()).compareTo(BigDecimal.ZERO) < 0
                || new BigDecimal(createProductDto.getDiscount()).compareTo(BigDecimal.ONE) > 0)) {
            appendErrorMessage(errors, PRODUCT_DISCOUNT, "Discount has to be in percentage range from 0 (exclusive) to 1 (exclusive)");
        }

        return errors;
    }
}
