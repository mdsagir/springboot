package com.green.springbootjwt.security;

import com.green.springbootjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {


    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public MyWebSecurity(@Qualifier("myUserDetailService") UserDetailsService userDetailsService,
                         JwtUtil jwtUtil, AuthenticationEntryPoint authenticationEntryPoint,
                         AuthenticationFailureHandler failureHandler,
                         AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFailureHandler = failureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilter(getUserLoginAuthenticationFilter())
                .addFilterAfter(new UserAuthenticationFilter(this.userDetailsService, this.jwtUtil), UserLoginAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/sign-up").permitAll()
                .antMatchers("/api/refresh-token").permitAll()
                .anyRequest()
                .authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    public UserLoginAuthenticationFilter getUserLoginAuthenticationFilter() throws Exception {
        final UserLoginAuthenticationFilter userLoginAuthenticationFilter = new UserLoginAuthenticationFilter(authenticationManagerBean());
        userLoginAuthenticationFilter.setFilterProcessesUrl("/api/login");
        userLoginAuthenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
        userLoginAuthenticationFilter.setAuthenticationFailureHandler(this.authenticationFailureHandler);
        userLoginAuthenticationFilter.setPostOnly(true);
        return userLoginAuthenticationFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
