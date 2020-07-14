package com.green.springbootjwt.util;

import org.apache.commons.lang3.RandomStringUtils;

public interface AppUtils {

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
