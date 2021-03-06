package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.user.Credentials;
import com.codecool.stockexchange.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoginService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginService(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager){
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity logUserIn(Credentials credentials) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
            List<String> roles =  auth.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            String token = jwtTokenUtil.createJsonWebToken(credentials.getUsername(), roles);
            Map<Object, Object> model = new HashMap<>();
            model.put("username", credentials.getUsername());
            model.put("roles", roles);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e){
            log.warn("Invalid user credentials");
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
