package com.docker;

import com.docker.config.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableConfigurationProperties
public class SpringbootDockerApplication {

    private final User user;

    public SpringbootDockerApplication(User user) {
        this.user = user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDockerApplication.class, args);
    }

    @GetMapping("message")
    public Map<String, String> doSomethingAfterStartup() {
        String message = user.getMessage();
        return Collections.singletonMap("message", message);

    }

}
