package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "${cors.allowed.path}")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/{user_id}")
    public User getUserById(@PathVariable Long user_id){
        return userService.findUserById(user_id);
    }

}
