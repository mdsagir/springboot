package com.green.springbootjwt.filter;

import com.green.springbootjwt.service.MyUserDetailService;
import com.green.springbootjwt.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final MyUserDetailService myUserDetailService;
    private final JwtUtil jwtUtil;

    public UserAuthenticationFilter(MyUserDetailService myUserDetailService, JwtUtil jwtUtil) {
        this.myUserDetailService = myUserDetailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeaders = request.getHeader("Authorization");
        String jwt = null;
        String username = null;


        if (Objects.nonNull(authorizationHeaders) && authorizationHeaders.startsWith("Bearer ")) {
            jwt = authorizationHeaders.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = this.myUserDetailService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}
