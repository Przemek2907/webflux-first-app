package com.app.infrastructure.security.config;

import com.app.infrastructure.security.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Flux;

@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;
    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()

                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)

                .exceptionHandling()
                .authenticationEntryPoint(serverAuthenticationEntryPoint())
                .accessDeniedHandler(serverAccessDeniedHandler())

                .and()
                .authorizeExchange()
                .pathMatchers("/users/registration/**", "/login").permitAll()
                .pathMatchers("/orders/**").hasAnyRole("USER")
                .pathMatchers("/products/**").hasAnyRole("ADMIN")
                .and().build();
    }

    @Bean
    public ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
        return (serverWebExchange, e) -> {
            try {
                var error = ErrorDto.builder().error(e.getMessage()).build();
                var errorJson = new ObjectMapper().writeValueAsString(error);
                DataBuffer dataBuffer = serverWebExchange.getResponse().bufferFactory().wrap(errorJson.getBytes());
                serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return serverWebExchange.getResponse().writeWith(Flux.just(dataBuffer));
            } catch (Exception ee) {
                DataBuffer dataBuffer = serverWebExchange.getResponse().bufferFactory().wrap("SERVER AUTHENTICATION ENTRY POINT EXCEPTION".getBytes());
                serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return serverWebExchange.getResponse().writeWith(Flux.just(dataBuffer));
            }
        };
    }

    @Bean
    public ServerAccessDeniedHandler serverAccessDeniedHandler() {
        return (serverWebExchange, e) -> {
            try {
                var error = ErrorDto.builder().error(e.getMessage()).build();
                var errorJson = new ObjectMapper().writeValueAsString(error);
                DataBuffer dataBuffer = serverWebExchange.getResponse().bufferFactory().wrap(errorJson.getBytes());
                serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return serverWebExchange.getResponse().writeWith(Flux.just(dataBuffer));
            } catch (Exception ee) {
                DataBuffer dataBuffer = serverWebExchange.getResponse().bufferFactory().wrap("SERVER ACCESS DENIED HANDLER EXCEPTION".getBytes());
                serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return serverWebExchange.getResponse().writeWith(Flux.just(dataBuffer));
            }
        };
    }

}
