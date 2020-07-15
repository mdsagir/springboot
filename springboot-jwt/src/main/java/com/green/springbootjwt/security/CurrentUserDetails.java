package com.green.springbootjwt.security;

import com.green.springbootjwt.entity.User;
import com.green.springbootjwt.exception.GenericException;
import com.green.springbootjwt.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserDetails {

    public CurrentUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    public User currentUser() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.
                getContext().
                getAuthentication().
                getPrincipal();
        final String username = userDetails.getUsername();

        Optional<User> user = userRepository.findByEmail(username);
        user.orElseThrow(() -> new GenericException("user not found"));
        return user.get();

    }
}
