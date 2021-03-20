package com.app.application.service;

import com.app.application.exception.RegisterUserServiceException;
import com.app.domain.configs.validator.generic.Validator;
import com.app.domain.configs.validator.generic.ValidatorException;
import com.app.domain.user.User;
import com.app.domain.user.UserRepository;
import com.app.domain.user.dto.RegisterUserDto;
import com.app.domain.user.dto.RegisterUserDtoResponse;
import com.app.domain.user.dto.validator.RegisterUserDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<RegisterUserDtoResponse> registerUser(Mono<RegisterUserDto> registerUser) {

        if (registerUser == null) {
            return Mono.error(() -> new RegisterUserServiceException("Cannot register user. Object is null"));
        }

        return registerUser.flatMap(
                userDto -> {
                    Validator.validate(new RegisterUserDtoValidator(), userDto);

                    return userRepository
                            .findByLogin(userDto.getLogin())
                            .hasElement()
                            .flatMap(isPresent -> {
                                if (isPresent) {
                                    return Mono.error(() -> new RegisterUserServiceException("Login already exists"));
                                }
                                return create(userDto);
                            });

                }
        );

    }

    private Mono<RegisterUserDtoResponse> create(RegisterUserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        var user = userDto.toUser();

        return userRepository
                .save(user)
                .map(User::toRegisteredUserDtoResponse);
    }

}
