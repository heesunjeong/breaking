package com.dkt.breaking.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

/*    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {
        PasswordEncoder defaultEncoder = new StandardPasswordEncoder();
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        DelegatingPasswordEncoder passworEncoder = new DelegatingPasswordEncoder(
            "bcrypt", encoders);
        passworEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

        return passworEncoder;
    }*/

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange()
            .pathMatchers("/maps/**", "/login", "logout", "/").permitAll()
            .anyExchange().authenticated() //1
            .and().httpBasic() //2
            .and().formLogin() //3
            .and().build(); //4
    }
}
