package com.example.demo.configuration;

import com.example.demo.auth.Path;
import com.example.demo.security.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
@Slf4j
@Configuration
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Value("${app.jjwt.secret}")
    private String secret;

    private final String[] publicRoutes = {Path.baseUrl+"auth/sign-in",Path.baseUrl+"auth/sign-up"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchangeSpec ->
                        authorizeExchangeSpec.pathMatchers(HttpMethod.OPTIONS).permitAll()
                                .pathMatchers(publicRoutes).permitAll()
                                .anyExchange().authenticated()
                )
                 .addFilterAt(bearerAuthenticationFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
    private AuthenticationWebFilter bearerAuthenticationFilter(AuthenticationManager manager){
        AuthenticationWebFilter beAuthenticationWebFilter = new AuthenticationWebFilter(manager);
        beAuthenticationWebFilter.setServerAuthenticationConverter(new BearerTokenAuthenticationConvertor(new JwtHandler(secret)));
        beAuthenticationWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        return beAuthenticationWebFilter;
    }

}
