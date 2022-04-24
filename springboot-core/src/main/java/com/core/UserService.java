package com.core;

import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserInfo userInfo;

    public UserService(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void process() {
        System.out.println(userInfo.getName());
        System.out.println(userInfo.getAddress());
        System.out.println(userInfo.getMobile());
        System.out.println(userInfo.getBio());
        System.out.println(userInfo.getMessage());
    }
}
