package com.green.springbootjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.springbootjwt.response.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {


        SecurityContextHolder.clearContext();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("invalid_credentials");
        errorResponse.setError_description("Unauthorized Invalid username or password!");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String string = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.getWriter().write(string);

    }
}
