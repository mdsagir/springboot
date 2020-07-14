package com.green.springbootjwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.springbootjwt.entity.User;
import com.green.springbootjwt.repo.UserRepository;
import com.green.springbootjwt.request.AuthenticationRequest;
import com.green.springbootjwt.response.AuthenticationResponse;
import com.green.springbootjwt.response.ErrorResponse;
import com.green.springbootjwt.util.AppUtils;
import com.green.springbootjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public UserLoginAuthenticationFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        super.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
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
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException {

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        final String token = this.jwtUtil.generateToken(userDetails.getUsername());
        final String refresh_token = this.userRepository
                .findByEmail(userDetails.getUsername())
                .map(User::getRefreshToken).get();


        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccess_token(token);
        authenticationResponse.setToken_type("bearer");

        authenticationResponse.setRefresh_token(refresh_token);
        authenticationResponse.setExpires_in(AppUtils.tokenExpiryTime);
        authenticationResponse.setScope("create");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String string = objectMapper.writeValueAsString(authenticationResponse);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Pragma", "no-cache");
        response.getWriter().write(string);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
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
