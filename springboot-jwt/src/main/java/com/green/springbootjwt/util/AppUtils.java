package com.green.springbootjwt.util;

import org.apache.commons.lang3.RandomStringUtils;

public interface AppUtils {

    long tokenExpiryTime = 30000L * 30;
    String ERROR_PASSWORD_CONFIRM_MISS_MATCH = "Password & confirm password must be same";
    String ERROR_EMAIL_EXISTS = "Email all ready exists";
    String TOKEN_TYPE = "Bearer ";
    String TOKEN_SCOPE = "create";

    static String generateRefreshToken() {
        StringBuilder refreshToken = new StringBuilder();

        String header = RandomStringUtils.randomAlphanumeric(20);
        String payload = RandomStringUtils.randomAlphanumeric(68);
        String signature = RandomStringUtils.randomAlphanumeric(45);

        refreshToken.append(header);
        refreshToken.append(".");
        refreshToken.append(payload);
        refreshToken.append(".");
        refreshToken.append(signature);
        return refreshToken.toString();
    }
}
