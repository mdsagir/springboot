package com.green.springbootjwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.springbootjwt.request.AuthenticationRequest;
import com.green.springbootjwt.response.AuthenticationResponse;
import com.green.springbootjwt.util.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginAuthenticationFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {

            final AuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);

            final Authentication authentication = new UsernamePasswordAuthenticationToken
                    (authenticationRequest.getUsername(), authenticationRequest.getPassword());

            return this.authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = this.jwtUtil.generateToken(userDetails);

        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccess_token(token);
        authenticationResponse.setToken_type("bearer");
        authenticationResponse.setRefresh_token(token);
        authenticationResponse.setExpires_in(3600);
        authenticationResponse.setScope("create");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String string = objectMapper.writeValueAsString(authenticationResponse);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Pragma", "no-cache");

        response.getWriter().write(string);

    }

}
