package com.example;

import com.example.model.Welcome;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@SpringBootApplication
@EnableConfigurationProperties
public class SpringbootK8sApplication {

    private final Welcome welcome;

    public SpringbootK8sApplication(Welcome welcome) {
        this.welcome = welcome;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootK8sApplication.class, args);
    }

    @GetMapping("message")
    public Map<String, String> message() {
        return Collections.singletonMap("message", welcome.getMessage());
    }


}
