package com.reactive.controller;

import com.reactive.document.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Slf4j
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void test_user_names() {
        webTestClient.get().uri("/user-count")
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void test_users_validations() {

        webTestClient.get().uri("/users")
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(User.class)
                .hasSize(3)
                .consumeWith(user -> {
                    List<User> userList = user.getResponseBody();
                    assert userList != null;
                    userList.forEach(u -> {
                        Assertions.assertNotNull(u.getId());
                        Assertions.assertNotNull(u.getName());
                    }
                    );
                });
    }
    @Test
    public void test_users_validations_step_verifier() {

        Flux<User> userFlux = webTestClient.get().uri("/users")
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .returnResult(User.class)
                .getResponseBody();

        StepVerifier
                .create(userFlux)
                /*.expectNext(new User("1","Sara"))
                .expectNext(new User("2","John"))
                .expectNext(new User("3","Smith"))*/
                .verifyComplete();

    }
}
