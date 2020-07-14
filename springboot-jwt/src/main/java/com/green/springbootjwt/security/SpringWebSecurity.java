package com.green.springbootjwt.security;

import com.green.springbootjwt.filter.UserAuthenticationFilter;
import com.green.springbootjwt.filter.UserLoginAuthenticationFilter;
import com.green.springbootjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@EnableWebSecurity
public class SpringWebSecurity extends WebSecurityConfigurerAdapter {


    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SpringWebSecurity(@Qualifier("myUserDetailService") UserDetailsService userDetailsService,
                             JwtUtil jwtUtil, AuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationEntryPoint = authenticationEntryPoint;
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

                //.addFilter(new UserLoginAuthenticationFilter(this.jwtUtil, authenticationManagerBean()))
                .addFilter(getUserLoginAuthenticationFilter())
                .addFilterAfter(new UserAuthenticationFilter(this.userDetailsService, this.jwtUtil), UserLoginAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    // TODO only for test purpose
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public UserLoginAuthenticationFilter getUserLoginAuthenticationFilter() throws Exception {
        final UserLoginAuthenticationFilter filter = new UserLoginAuthenticationFilter(this.jwtUtil, authenticationManagerBean());
        filter.setFilterProcessesUrl("/api/login");
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
