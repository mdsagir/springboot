package com.green.springbootjwt.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyUserDetailService implements UserDetailsService {


    private  static final Map<String, UserDetails> userDetailsMap = new ConcurrentHashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (this.userDetailsMap.containsKey(username)) {
            UserDetails userDetails = userDetailsMap.get(username);
            return new User(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
        }

        throw new UsernameNotFoundException("user not found");

    }
    static {
        Arrays.asList("john", "sara").forEach(user ->
                userDetailsMap.putIfAbsent(user, new User(user, "password",
                        AuthorityUtils.createAuthorityList("ROLE_USER"))));
    }


}
