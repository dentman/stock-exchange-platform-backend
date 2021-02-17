package com.codecool.stockexchange.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class JwtRequestFilter extends GenericFilterBean {

    private JwtTokenUtil jwtTokenUtil;
    private TradeUserDetailsService tradeUserDetailsService;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, TradeUserDetailsService tradeUserDetailsService){
        this.jwtTokenUtil = jwtTokenUtil;
        this.tradeUserDetailsService = tradeUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtToken = jwtTokenUtil.getTokenFromRequest((HttpServletRequest) request);

        if (jwtToken != null && jwtTokenUtil.validateToken(jwtToken)) {
            Claims claims = jwtTokenUtil.extractClaimsFromToken(jwtToken);

            List<SimpleGrantedAuthority> authorities = ((List<String>) claims.get("roles"))
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            CustomUser user = tradeUserDetailsService.loadUserByUsername(claims.getSubject());
            Authentication auth = new UsernamePasswordAuthenticationToken(user, "", authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

}
