package com.green.springbootjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WelcomeController {

    private final String GET_HELLO = "hello";

    @GetMapping(GET_HELLO)
    public String hello() {
        return "welcomeService.service()";
    }

}
