package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.Message;
import com.codecool.stockexchange.entity.user.User;
import com.codecool.stockexchange.exception.user.UserExistsException;
import com.codecool.stockexchange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Message registerUser(@RequestBody User user){
        userService.saveNewUser(user);
        return new Message(true, "User " + user.getUsername() + " registered");
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Message handleUserExistsException(UserExistsException e){return new Message(false, e.getMessage());}
}
