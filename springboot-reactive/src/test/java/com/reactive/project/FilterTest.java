package com.reactive.project;

import com.reactive.document.User;

public class FilterTest {

    public static void main(String[] args) {

        String id="2";

        User user=new User();

        user.setId(id);
        user.setMobile(9999);
        user.setEmail("email.com");

        user.setName(id.equals("1") ?  "sagir":"ansari" );

        System.out.println(user);

    }
}
