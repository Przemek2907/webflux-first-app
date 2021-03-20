package com.app.domain.user.dto.validator;

import com.app.domain.configs.validator.generic.Validator;
import com.app.domain.user.dto.RegisterUserDto;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserDtoValidator implements Validator<RegisterUserDto> {

    private static final String NULL_OBJECT_ERROR_KEY = "NO_OBJECT_PROVIDED";
    private static final String USER_LOGIN_ERROR = "USER_LOGIN_ERROR";
    private static final String PASSWORD_ERROR = "PASSWORD_ERROR";
    private static final String NAME_ERROR = "INCORRECT_NAME";
    private static final String SURNAME_ERROR = "INCORRECT_SURNAME";

    @Override
    public Map<String, String> validate(RegisterUserDto registerUserDto) {
        var errors = new HashMap<String, String>();

        if (registerUserDto == null) {
            appendErrorMessage(errors, NULL_OBJECT_ERROR_KEY, "Register user object is null");
        } else {

            if (registerUserDto.getName() == null || registerUserDto.getName().trim().length() == 0) {
                appendErrorMessage(errors, NAME_ERROR, "User's name is missing");
            }

            if (registerUserDto.getLogin() == null || registerUserDto.getLogin().trim().length() == 0) {
                appendErrorMessage(errors, USER_LOGIN_ERROR, "User login is missing");
            }

            if (registerUserDto.getLogin() != null && registerUserDto.getLogin().trim().length() < 4) {
                appendErrorMessage(errors, USER_LOGIN_ERROR, "User login has to contain at least 4 characters");
            }

            if (registerUserDto.getPassword() == null || registerUserDto.getPassword().trim().length() == 0) {
                appendErrorMessage(errors, PASSWORD_ERROR, "Password is missing");
            }

            if (registerUserDto.getPassword() != null && registerUserDto.getPassword().trim().length() < 6) {
                appendErrorMessage(errors, PASSWORD_ERROR, "Password has to contain at least 6 characters");
            }

            if (registerUserDto.getPassword() != null && !registerUserDto.getPassword().equals(registerUserDto.getPasswordConfirmation())) {
                appendErrorMessage(errors, PASSWORD_ERROR, "Password and password confirmation data differs");
            }
        }

        return errors;
    }
}
