package com.green.springbootjwt.service;

import org.springframework.stereotype.Service;

@Service
public class WelcomeService {

    private final UserFacade userFacade;

    public WelcomeService(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public String service() {
        return userFacade.getAuthentication().getName();
    }
}