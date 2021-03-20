package com.app.unitTests;

import com.app.domain.order.dto.CreateOrderDto;
import com.app.domain.order.dto.validator.CreateOrderDtoValidator;
import com.app.domain.configs.validator.generic.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ValidatorTest {

    Validator<CreateOrderDto> validatorUnderTest = new CreateOrderDtoValidator();

    @Test
    @DisplayName("Test if error message is appended to an existing key")
    public void shouldAppendErrorMessage_WhenErrorKeyExists() {

        //GIVEN
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("MYERROR", "My error value1.");


        //WHEN
        validatorUnderTest.appendErrorMessage(errorsMap, "MYERROR", "My second error value - no2.");


        //THEN
        Assertions.assertEquals("My error value1. My second error value - no2.", errorsMap.get("MYERROR"));

    }

    @Test
    @DisplayName("Test if error appender is initialized when first error message is being added")
    public void shouldInsertFirstErrorKey_WhenFirstValidationErrorEncountered() {
        //GIVEN
        Map<String, String> errorsMap = new HashMap<>();

        //WHEN
        validatorUnderTest.appendErrorMessage(errorsMap, "MYERROR", "My first error message with yet unique key");

        //THEN
        Assertions.assertEquals("My first error message with yet unique key", errorsMap.get("MYERROR"));


    }
}
