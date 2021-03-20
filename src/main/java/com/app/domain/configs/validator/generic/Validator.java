package com.app.domain.configs.validator.generic;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public interface Validator<T> {
    Map<String, String> validate(T t);

    default void appendErrorMessage(Map<String, String> errors, String examinedKey, String errorMessage) {
        if (errors == null) {
            return;
        }

        if (errors.containsKey(examinedKey)) {
            String existingErrors = errors.get(examinedKey);
            String messageAfterAppend = existingErrors + " " + errorMessage;
            errors.put(examinedKey, messageAfterAppend);
        } else {
            errors.put(examinedKey, errorMessage);
        }
    }

    static <T> void validate(Validator<T> validator, T t) {
        var errors = validator.validate(t);

        if (!errors.isEmpty()) {
            var message = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ValidatorException("[VALIDATION ERRORS]: " + message);
        }
    }
}
