package com.greenjava.validationexception.request;

import javax.validation.constraints.Size;

public class User {


    private Integer id;
    @Size(min = 2,message = "Name minimum 2 character required")
    private String name;
    @Size(min = 2,message = "mobile minimum required 2 character")
    private String mobile;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
