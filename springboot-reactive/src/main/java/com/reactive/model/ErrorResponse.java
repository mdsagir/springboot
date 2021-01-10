package com.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {
    private String message;
    private LocalDateTime localDate = LocalDateTime.now();

    public ErrorResponse(String message) {
        this.message=message;
    }
}
