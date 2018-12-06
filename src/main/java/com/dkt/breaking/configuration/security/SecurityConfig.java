package com.dkt.breaking.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private securityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .csrf().disable()
            .logout().disable()
            .formLogin().disable()/*.loginPage("/login")
                .authenticationFailureHandler((exchange, exception) -> Mono.error(exception))
                .authenticationSuccessHandler(new WebFilterChainServerAuthenticationSuccessHandler())
            .and()*/

            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .exceptionHandling()
                .authenticationEntryPoint((exchange, exception) -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage(), exception)))
                .accessDeniedHandler((exchange, exception) -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage(), exception)))
            .and()

            .authorizeExchange()
                /*.pathMatchers("/user/login", "/user/register", "/maps/**").permitAll()
                .anyExchange().authenticated()*/
                .pathMatchers("/review/create").authenticated()
                .anyExchange().permitAll()

            .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
