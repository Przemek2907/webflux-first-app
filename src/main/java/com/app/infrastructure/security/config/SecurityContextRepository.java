package com.app.infrastructure.security.config;

import com.app.infrastructure.security.exception.AppSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(header, header);
            return this.authenticationManager
                    .authenticate(auth)
                    .map(SecurityContextImpl::new);
        }
        return Mono.error(new AppSecurityException("Authorization failed!"));
    }
}
