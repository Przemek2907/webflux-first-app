package com.app.domain.order.dto.validator;

import com.app.domain.order.dto.CreateOrderDto;
import com.app.domain.configs.validator.generic.Validator;

import java.util.HashMap;
import java.util.Map;

public class CreateOrderDtoValidator implements Validator<CreateOrderDto> {

    private static final String NULL_OBJECT_ERROR_KEY = "NO_OBJECT_PROVIDED";
    private static final String NO_PRODUCT_IDS = "MISSING_PRODUCT_ID";
    private static final String QUANTITY_ERROR = "INCORRECT_QUANTITY";

    @Override
    public Map<String, String> validate(CreateOrderDto createOrderDto) {
        var errors = new HashMap<String, String>();

        if (createOrderDto == null || createOrderDto.getProductWithQuantities() == null || createOrderDto.getProductWithQuantities().size() == 0) {
            appendErrorMessage(errors, NULL_OBJECT_ERROR_KEY, "There is no order to be processed");
        } else {
            if (createOrderDto.getProductWithQuantities().stream().anyMatch(product -> product.getId() == null)) {
                appendErrorMessage(errors, NO_PRODUCT_IDS, "At least one product is missing id");
            }

            if (createOrderDto.getProductWithQuantities().stream().anyMatch(product -> product.getQuantity() == null || product.getQuantity() <=0)) {
                appendErrorMessage(errors, QUANTITY_ERROR, "Quantity for at least one product is missing or is less than 1");
            }
        }

        return errors;
    }
}
