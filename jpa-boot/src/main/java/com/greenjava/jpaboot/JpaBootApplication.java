package com.greenjava.jpaboot;

import com.greenjava.jpaboot.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaBootApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(JpaBootApplication.class, args);
    }

    @Autowired
    private UsersService usersService;

    @Override
    public void run(String... args) throws Exception {
        usersService.jpaTest();
    }
}
