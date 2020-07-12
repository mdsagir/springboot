package com.green.springbootjwt.controller;

import com.green.springbootjwt.request.AuthenticationRequest;
import com.green.springbootjwt.response.AuthenticationResponse;
import com.green.springbootjwt.service.MyUserDetailService;
import com.green.springbootjwt.service.WelcomeService;
import com.green.springbootjwt.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {


    private final AuthenticationManager authenticationManager;
    private final MyUserDetailService myUserDetailService;
    private final JwtUtil jwtUtil;
    private final WelcomeService welcomeService;

    private final String GET_HELLO = "hello";
    private final String POST_AUTHENTIC = "authenticate";

    public WelcomeController(AuthenticationManager authenticationManager,
                             MyUserDetailService myUserDetailService,
                             JwtUtil jwtUtil,
                             WelcomeService welcomeService) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailService = myUserDetailService;
        this.jwtUtil = jwtUtil;
        this.welcomeService = welcomeService;
    }

    @GetMapping(GET_HELLO)
    public String hello() {
        return welcomeService.service();
    }

    @PostMapping(POST_AUTHENTIC)
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("username or password incorrect", e);
        }
        final UserDetails userDetails = this.myUserDetailService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
