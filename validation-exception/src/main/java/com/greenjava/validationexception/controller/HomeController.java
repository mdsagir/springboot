package com.greenjava.validationexception.controller;

import com.greenjava.validationexception.excepton.BadRequestException;
import com.greenjava.validationexception.request.CustomValidator;
import com.greenjava.validationexception.request.Student;
import com.greenjava.validationexception.request.User;
import com.greenjava.validationexception.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public abstract class HomeController {

    @GetMapping("home/{id}")
    public String home(@PathVariable("id") int id) {

        if (id == 1) {
            throw new BadRequestException("id ==1 Bad Request Exception");
        }

        if (id == 2) {
//            String s=null;
//            s.length();
//
            int[] a = {1, 3};
            System.out.println(a[2]);
        }
        return "hello world";
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {

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
    public Student createStudent(@Valid @RequestBody Student student) {

        return student;
    }

//    @Autowired
//    private HomeService homeService;

    @GetMapping("/docker")
    public String docker() {
        System.out.println("@"+getHomeService().hashCode());
        //System.out.println("Request is coming !!!");
        return "docker";
    }

    @Lookup
    public abstract HomeService getHomeService();

    @GetMapping("/hello")
    public List<Student> header() {
        Student student = new Student(1, "Rama");
        System.out.println(student);
        List<Student> students = Arrays.asList(student);
        return students;
    }

    @GetMapping("test")
    public User user() {
        User user = new User();
        user.setId(1);
        user.setMobile("0900");
        user.setName("sahoo");
        return user;
    }



    @PostMapping("/post-student")
    public List<Student> testStudent(@RequestHeader("X-ID") String id,
                                     @RequestBody Student student) {

        String s = getHomeService().homeService();
        System.out.println(s);
        System.out.println("id: "+id);
        System.out.println("user name: "+student.getName());
        return Arrays.asList(student);
    }

}
