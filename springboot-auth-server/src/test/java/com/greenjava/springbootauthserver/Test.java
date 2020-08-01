package com.greenjava.springbootauthserver;



import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        String path="/Users/sagir/Documents/springboot/springboot/springboot-auth-server/src/main/resources/jwt.jks";

        File file=new File(path);
        System.out.println(file.getName());

        File file2 = ResourceUtils.getFile("classpath:jwt.jks");
        System.out.println(file2.getName());


//        ClassPathResource classPathResource=new ClassPathResource("jwt.jks");
//        System.out.println(classPathResource.getFile().getName());

    }
}
