package com.reactive.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document("user")
public class User {

    @Id
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    private String email;
    private int mobile;
}
