package com.gmail.portnova.julia.web.config;

import com.gmail.portnova.julia.repository.model.RoleNameEnum;
import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@Order(1)
@RequiredArgsConstructor
public class AppAPIConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeRequests().anyRequest()
                .hasAuthority(RoleNameEnumDTO.SECURE_REST_API.name())
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }
}
