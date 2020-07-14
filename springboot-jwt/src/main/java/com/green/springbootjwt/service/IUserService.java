package com.green.springbootjwt.service;

import com.green.springbootjwt.entity.User;
import com.green.springbootjwt.request.RefreshTokenRequest;
import com.green.springbootjwt.request.SignUpRequest;
import com.green.springbootjwt.response.AccessToken;

public interface IUserService {

    User signUp(SignUpRequest user);
    AccessToken refreshToken(RefreshTokenRequest refreshTokenRequest);
}
