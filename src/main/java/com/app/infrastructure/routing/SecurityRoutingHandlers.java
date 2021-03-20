package com.app.infrastructure.routing;

import com.app.application.service.RegisterUserService;
import com.app.domain.user.UserFunctors;
import com.app.domain.user.UserRepository;
import com.app.infrastructure.security.dto.AuthenticationDto;
import com.app.infrastructure.security.exception.AppSecurityException;
import com.app.infrastructure.security.service.AppTokensService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityRoutingHandlers {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppTokensService appTokensService;

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        Mono<AuthenticationDto> authenticationDtoMono = serverRequest.bodyToMono(AuthenticationDto.class);
        return authenticationDtoMono
                .flatMap(authenticationDto -> userRepository
                        .findByLogin(authenticationDto.getUsername())
                        .flatMap(user -> {
                            if (!passwordEncoder.matches(authenticationDto.getPassword(), UserFunctors.toPassword.apply(user))) {

                                System.out.println(authenticationDto.getPassword());
                                System.out.println(UserFunctors.toPassword.apply(user));
                                return Mono.error(new AppSecurityException("Password is not correct"));
                            }
                            return RoutingHandlersUtil.toServerResponse(appTokensService.generateTokens(authenticationDto), HttpStatus.OK);
                        })
                );
    }
}
