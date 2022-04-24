package com.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "userinfo")
public class UserInfo {

    /**
     * Current login username
     */
    private String name;

    /**
     * Current login user Address
     */
    private List<String> address;

    /**
     * This is user biography
     */
    private String bio;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Mobile No: Personal and Official
     */
    private List<Long> mobile;

    public List<Long> getMobile() {
        return mobile;
    }

    public void setMobile(List<Long> mobile) {
        this.mobile = mobile;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
