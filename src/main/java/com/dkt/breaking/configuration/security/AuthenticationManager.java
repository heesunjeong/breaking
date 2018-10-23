package com.dkt.breaking.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.UnsupportedJwtException;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;
        try {
            username = jwtTokenProvider.getUsernameFromToken(authToken);
        } catch (Exception e) {
            username = null;
        }
        if (username != null && jwtTokenProvider.validateToken(authToken)) {
            Claims claims = jwtTokenProvider.getAllClaimsFromToken(authToken);

            //TODO 권한 체크
            List<String> rolesMap = claims.get("role", List.class);
           /* List<Role> roles = new ArrayList<>();

            for (String role : rolesMap) {
                roles.add(Role.valueOf(role));
            }*/

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                null);
            return Mono.just(auth);
        } else {
            return Mono.error(new UnsupportedJwtException("Not Match Signature"));
        }
    }
}
