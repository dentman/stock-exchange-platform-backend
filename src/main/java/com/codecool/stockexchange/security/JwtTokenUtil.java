package com.codecool.stockexchange.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenUtil {

    @Value("${spring.jwt.signingKey}")
    private String secretKey;

    @Value("${spring.jwt.token.validity}")
    private Long validityInMs;

    @PostConstruct
    private void encodeKey(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createJsonWebToken(String username, List<String> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validTill = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validTill)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) return header.substring(7);
        return null;
    }

    public boolean validateToken(String token){
        try {
            Claims body = getTokenBody(token);
            if (body.getExpiration().after(new Date())) return true;
        } catch (JwtException | IllegalArgumentException e){
            log.info("Invalid token");
        }
        return false;
    }

    public Authentication extractUserFromToken(String token){
        Claims claims = getTokenBody(token);
        List<SimpleGrantedAuthority> authorities = ((List<String>) claims.get("roles"))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

    private Claims getTokenBody(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

}
