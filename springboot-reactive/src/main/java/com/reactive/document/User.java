package com.reactive.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document("user")
public class User {

    @Id
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private String email;
    private int mobile;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }
}
