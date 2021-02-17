package com.codecool.stockexchange.controller;

import com.codecool.stockexchange.entity.user.Credentials;
import com.codecool.stockexchange.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(value = "${cors.allowed.path}")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity logUserIn(@RequestBody Credentials credentials){
        return loginService.logUserIn(credentials);
    }

}
