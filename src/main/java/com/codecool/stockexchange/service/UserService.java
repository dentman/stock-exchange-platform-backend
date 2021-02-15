package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.Message;
import com.codecool.stockexchange.entity.user.Account;
import com.codecool.stockexchange.entity.user.Role;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.exception.user.UserExistsException;
import com.codecool.stockexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder pwe = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    public User findUserById(Long user_id) {
        return userRepository.findUserById(user_id);
    }

    @Transactional
    public User saveNewUser(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        if (userOptional.isEmpty()){
            Account account = Account.builder().balance(BigDecimal.valueOf(10000)).currency("USD").build();
            account.setUser(user);
            user.setPassword(pwe.encode(user.getPassword()));
            user.setAccount(account);
            user.setRoles(List.of(Role.ROLE_USER));
            userRepository.save(user);
            return user;
        }
        throw new UserExistsException("Username " + user.getUsername() + " already exists");
    }
}
