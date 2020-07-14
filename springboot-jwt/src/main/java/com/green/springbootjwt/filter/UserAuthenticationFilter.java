package com.green.springbootjwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.springbootjwt.response.ErrorResponse;
import com.green.springbootjwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
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

        if (Objects.nonNull(authorizationHeaders) && StringUtils.startsWithIgnoreCase(authorizationHeaders, "bearer ")) {
            jwt = authorizationHeaders.substring(7);
            try {
                username = this.jwtUtil.extractUsername(jwt);
            } catch (ExpiredJwtException e) {
                expireTokenResponse(response);
                return;
            }
        }
        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (this.jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }
        filterChain.doFilter(request, response);
    }

    public void expireTokenResponse(HttpServletResponse response) {

        final ObjectMapper objectMapper = new ObjectMapper();
        final ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setError("invalid_token");
        errorResponse.setError_description("The access token expired");

        String responseString;

        try {
            responseString = objectMapper.writeValueAsString(errorResponse);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.getWriter().write(responseString);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
