package com.green.springbootjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.springbootjwt.response.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exception) throws IOException {

        final ObjectMapper objectMapper = new ObjectMapper();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("unauthorized");
        errorResponse.setError_description(exception.getMessage());

        String responseString = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.getWriter().write(responseString);

    }
}
