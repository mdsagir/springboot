package com.greenjava.validationexception.controller;

import com.greenjava.validationexception.excepton.BadRequestException;
import com.greenjava.validationexception.request.CustomValidator;
import com.greenjava.validationexception.request.Student;
import com.greenjava.validationexception.request.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class HomeController {

    @GetMapping("home/{id}")
    public String home(@PathVariable("id") int id) {

        if (id == 1) {
            throw new BadRequestException("id ==1 Bad Request Exception");
        }

        if (id==2){
//            String s=null;
//            s.length();
//
            int[] a={1,3};
            System.out.println(a[2]);
        }
        return "hello world";
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user){

        return user;
    }
    @Autowired
    private CustomValidator customValidator;
    @InitBinder
    private void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(customValidator);
    }
    @PostMapping("student")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@Valid @RequestBody Student student){

        return student;
    }

}
