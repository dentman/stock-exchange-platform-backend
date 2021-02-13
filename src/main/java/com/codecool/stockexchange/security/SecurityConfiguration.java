package com.codecool.stockexchange.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder authManager) throws Exception {
        PasswordEncoder encoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
        authManager
            .inMemoryAuthentication()
            .withUser("user")
            .password(encoder.encode("password"))
            .roles("USER")
            .and()
            .withUser("admin")
            .password(encoder.encode("admin"))
            .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
                .csrf().disable()
            .authorizeRequests()
                .antMatchers("/trade/*").authenticated()
                .antMatchers("/user/*").authenticated()
                .antMatchers("/quote/*").permitAll()
                .antMatchers("/stock/*").permitAll();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
