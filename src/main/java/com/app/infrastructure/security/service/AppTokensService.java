package com.app.infrastructure.security.service;

import com.app.domain.user.UserFunctors;
import com.app.domain.user.UserRepository;
import com.app.infrastructure.security.exception.AppSecurityException;
import com.app.infrastructure.security.dto.AuthenticationDto;
import com.app.infrastructure.security.dto.TokensDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@ConfigurationProperties(prefix = "tokens")
@Service
@RequiredArgsConstructor
public class AppTokensService {

    @Value("${tokens.access-token.expiration-date-ms}")
    private Long accessTokenExpirationDateMs;

    @Value("${tokens.refresh-token.expiration-date-ms}")
    private Long refreshTokenExpirationDateMs;

    @Value("${tokens.refresh-token.property}")
    private String refreshTokenProperty;

    @Value("${tokens.bearer}")
    protected String tokenBearer;

    private final SecretKey secretKey;

    private final UserRepository userRepository;

    // -------------------------------------------------------------------------------------------------------------
    // GENEROWANIE TOKENA
    // -------------------------------------------------------------------------------------------------------------
    public Mono<TokensDto> generateTokens(AuthenticationDto authenticationDto) {

        if (authenticationDto == null) {
            return Mono.error(() -> new AppSecurityException("Authentication data is null"));
        }

        return userRepository
                .findByLogin(authenticationDto.getUsername())
                .flatMap(user -> {
                    var currentDateMs = System.currentTimeMillis();
                    var currentDate = new Date(currentDateMs);

                    var accessTokensDateMs = currentDateMs + accessTokenExpirationDateMs;
                    var accessTokenDate = new Date(accessTokensDateMs);

                    var refreshTokenDateMs = currentDateMs + refreshTokenExpirationDateMs;
                    var refreshTokenDate = new Date(refreshTokenDateMs);

                    var accessToken = Jwts
                            .builder()
                            .setSubject(UserFunctors.toId.apply(user))
                            .setExpiration(accessTokenDate)
                            .setIssuedAt(currentDate)
                            .signWith(secretKey)
                            .compact();

                    var refreshToken = Jwts
                            .builder()
                            .setSubject(UserFunctors.toId.apply(user))
                            .setExpiration(refreshTokenDate)
                            .setIssuedAt(currentDate)
                            .signWith(secretKey)
                            .compact();

                    return Mono.just(TokensDto
                            .builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build());
                });
    }

    // -------------------------------------------------------------------------------------------------------------
    // PARSOWANIE TOKENA
    // -------------------------------------------------------------------------------------------------------------

    public Mono<Authentication> parse(String header) {
        if (header == null) {
            return Mono.error(() -> new AppSecurityException("Access token is null"));
        }

        if (!header.startsWith(tokenBearer)) {
            return Mono.error(() -> new AppSecurityException("Access token has incorrect format"));
        }

        var accessToken = header.replace(tokenBearer, "");

        if (hasTokenExpired(accessToken)) {
            return Mono.error(() -> new AppSecurityException("Your access token has expired"));
        }

        var userId = id(accessToken);
        if (userId == null) {
            return Mono.error(() -> new AppSecurityException("User id is null"));
        }
        return userRepository
                .findById(userId)
                .map(user -> {
                    var secUser = user.toSecurityUserDto();
                    return new UsernamePasswordAuthenticationToken(
                            secUser.getLogin(),
                            null,
                            secUser.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                });
    }

    private boolean hasTokenExpired(String token) {
        return new Date().after(expiration(token));
    }

    private Claims claims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date expiration(String token) {
        return claims(token).getExpiration();
    }

    private String id(String token) {
        return claims(token).getSubject();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(claims(token).getSubject());
    }

}
