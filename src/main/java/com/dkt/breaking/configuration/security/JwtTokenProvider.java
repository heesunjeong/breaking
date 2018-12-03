package com.dkt.breaking.configuration.security;

import com.dkt.breaking.service.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider implements Serializable {

    @Value("${app.jwt.Secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private String expirationTime;

    @Autowired
    private RedisService redisService;

    private String REDIS_KEY = "break:user:refresh:%s";

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public Claims getAllClaimsFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities());
        claims.put("enable", user.isEnabled());
        claims.put("id", ((BreakingUserDetails) user).getUser().getId());

        generateRefreshToken(((BreakingUserDetails) user).getUser().getId());

        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        Long expirationTimeLong = Long.parseLong(expirationTime); //in second
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(key)
            .compact();
    }

    private void generateRefreshToken(String userId) {
        String refreshToken = UUID.randomUUID().toString().replaceAll("-", "");
        redisService.setRedis(String.format(REDIS_KEY, userId), refreshToken);
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}