package com.green.springbootjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.springbootjwt.entity.User;
import com.green.springbootjwt.repo.UserRepository;
import com.green.springbootjwt.response.AuthenticationResponse;
import com.green.springbootjwt.util.AppUtils;
import com.green.springbootjwt.util.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public MyAuthenticationSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        final String token = this.jwtUtil.generateToken(userDetails.getUsername());
        final String refresh_token = this.userRepository
                .findByEmail(userDetails.getUsername())
                .map(User::getRefreshToken).get();


        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccess_token(token);
        authenticationResponse.setToken_type(AppUtils.TOKEN_TYPE);

        authenticationResponse.setRefresh_token(refresh_token);
        authenticationResponse.setExpires_in(AppUtils.tokenExpiryTime);
        authenticationResponse.setScope(AppUtils.TOKEN_SCOPE);

        final ObjectMapper objectMapper = new ObjectMapper();
        final String string = objectMapper.writeValueAsString(authenticationResponse);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Pragma", "no-cache");
        response.getWriter().write(string);
    }
}
