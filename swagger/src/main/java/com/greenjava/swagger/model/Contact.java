package com.greenjava.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Details about the contact")
public class Contact {

    @ApiModelProperty(notes = "This is the unique id for the contact")
    private String id;
    @ApiModelProperty(notes = "The person's id")
    private String name;
    @ApiModelProperty(notes = "The person's name")
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
