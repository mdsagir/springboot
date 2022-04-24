package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableConfigurationProperties(UserInfo.class)
public class SpringbootCoreApplication {

    private final UserService userService;

    public SpringbootCoreApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(SpringbootCoreApplication.class, args);
        configurableApplicationContext.close();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        userService.process();
    }

}
