package com.green.springbootjwt.request;

import com.green.springbootjwt.annotation.UserValidatorAnnotation;

@UserValidatorAnnotation(
        email="email",
        password = "password",
        confirmPassword = "confirmPassword"
)
public class SignUpRequest {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String confirmPassword;

    public SignUpRequest() {
    }

    public SignUpRequest(Integer id, String name, String email, String password, String confirmPassword) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
