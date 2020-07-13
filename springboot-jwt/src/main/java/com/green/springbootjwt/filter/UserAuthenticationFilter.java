package com.green.springbootjwt.filter;

import com.green.springbootjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class UserAuthenticationFilter extends OncePerRequestFilter {


    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public UserAuthenticationFilter(@Qualifier("myUserDetailService") UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeaders = request.getHeader("Authorization");
        String jwt = null;
        String username = null;


        if (Objects.nonNull(authorizationHeaders) && StringUtils.startsWithIgnoreCase(authorizationHeaders, "bearer ")  ) {
            jwt = authorizationHeaders.substring(7);
            username = this.jwtUtil.extractUsername(jwt);
        }
        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (this.jwtUtil.validateToken(jwt, userDetails)) {
                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}
