package com.javagreen.requestbody;

import com.javagreen.controller.UserNameValidatorAnnotation;

import javax.validation.constraints.NotEmpty;

@UserNameValidatorAnnotation(
        password = "password",
        confirmationPassword = "confirmationPassword"
)
public class User {

    @NotEmpty(message = "Name not be null or empty.")
    private String name;
    private String email;
    private String password;
    private String confirmationPassword;
    private String mobile;
    @NotEmpty(message = "City not be null or empty.")
    private String city;

    public User(String name, String email, String password, String confirmationPassword, String mobile, String city) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmationPassword = confirmationPassword;
        this.mobile = mobile;
        this.city = city;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmationPassword='" + confirmationPassword + '\'' +
                ", mobile='" + mobile + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
