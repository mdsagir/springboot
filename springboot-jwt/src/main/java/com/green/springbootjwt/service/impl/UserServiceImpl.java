package com.green.springbootjwt.service.impl;

import com.green.springbootjwt.entity.User;
import com.green.springbootjwt.exception.GenericException;
import com.green.springbootjwt.repo.UserRepository;
import com.green.springbootjwt.request.RefreshTokenRequest;
import com.green.springbootjwt.request.SignUpRequest;
import com.green.springbootjwt.response.AccessToken;
import com.green.springbootjwt.service.IUserService;
import com.green.springbootjwt.util.AppUtils;
import com.green.springbootjwt.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User signUp(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setRefreshToken(AppUtils.generateRefreshToken());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AccessToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
        // TODO : Validate client id & secret id

         return userRepository.findByRefreshToken(refreshTokenRequest.getRefresh_token())
                .map(user -> new AccessToken(jwtUtil.generateToken(user.getEmail()),
                        "bearer",AppUtils.tokenExpiryTime,"create"))
                .orElseThrow(() -> new GenericException("token is not available"));

    }
}
