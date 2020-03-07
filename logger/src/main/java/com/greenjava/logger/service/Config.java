package com.greenjava.logger.service;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Config {

    public static void  init(){
        System.setProperty("timestamp", getLoggerDate());
        String fileName= System.getProperty("user.dir");

        try (Stream<String> stream = Files.lines(Paths.get(fileName+"/.env"))) {

            List<String> stringList = stream.collect(Collectors.toList());

            for (String s : stringList) {
                if (!s.isEmpty()) {
                    String key = s.substring(0, s.indexOf('='));
                    String value = s.substring(key.length() + 1);
                    System.setProperty(key, value);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String getLoggerDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(new Date());

    }
}
