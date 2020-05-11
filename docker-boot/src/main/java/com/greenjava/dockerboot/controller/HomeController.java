package com.greenjava.dockerboot.controller;

import com.greenjava.dockerboot.request.Student;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("home")
    public Student home() {
        Student student = new Student();
        student.setId(1);
        student.setName("Rama");
        return student;
    }
}
