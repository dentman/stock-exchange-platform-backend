package com.codecool.stockexchange.security;

import com.codecool.stockexchange.repository.UserRepository;
import com.codecool.stockexchange.entity.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TradeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public TradeUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("This email does not exist"));

        return new CustomUser(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority(r.toString()))
                        .collect(Collectors.toList()),
                user.getId()
        );
    }
}
