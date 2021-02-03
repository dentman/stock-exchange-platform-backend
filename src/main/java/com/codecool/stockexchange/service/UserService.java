package com.codecool.stockexchange.service;

import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserById(Long user_id) {
        return userRepository.findUserById(user_id);
    }
}
