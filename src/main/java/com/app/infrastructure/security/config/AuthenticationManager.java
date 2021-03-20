package com.app.infrastructure.security.config;

import com.app.application.service.RegisterUserService;
import com.app.infrastructure.security.exception.AppSecurityException;
import com.app.infrastructure.security.service.AppTokensService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final AppTokensService appTokensService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        try {
            String header = authentication.getCredentials().toString();
            return appTokensService.parse(header);
        } catch (Exception e) {
            return Mono.error(() -> new AppSecurityException(e.getMessage()));
        }
    }
}
