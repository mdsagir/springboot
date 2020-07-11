package com.javagreen.controller;

import com.javagreen.requestbody.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HomeController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("user")
    public User user(@RequestBody @Valid User user) {

        if (user.getName().equals("john")) throw new UserExistException("user all ready taken");
        return user;
    }
}
