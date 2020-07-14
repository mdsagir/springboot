package com.green.springbootjwt.controller;

import com.green.springbootjwt.entity.User;
import com.green.springbootjwt.request.RefreshTokenRequest;
import com.green.springbootjwt.request.SignUpRequest;
import com.green.springbootjwt.response.AccessToken;
import com.green.springbootjwt.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {


    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("sign-up")
    public User signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }

    @PostMapping("refresh-token")
    public AccessToken refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest);

    }

}
