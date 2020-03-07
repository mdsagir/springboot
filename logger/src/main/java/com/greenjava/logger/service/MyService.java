package com.greenjava.logger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Value("${data}")
    String data;

    @Value("${good}")
    String good;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyService.class);

    public void serviceCall() {

        LOGGER.info("from properties file "+data);
        LOGGER.info("from properties file "+good);

    }
}
