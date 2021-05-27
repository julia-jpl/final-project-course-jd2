package com.gmail.portnova.julia.web.config;


import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import com.gmail.portnova.julia.web.handler.LoginAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
@Profile("!test")
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    public AppSecurityConfig(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/**")
                .hasAuthority(RoleNameEnumDTO.ADMINISTRATOR.name())
                .antMatchers( "/profile/**", "/customer/**")
                .hasAuthority(RoleNameEnumDTO.CUSTOMER_USER.name())
                .antMatchers("/sale/**")
                .hasAuthority(RoleNameEnumDTO.SALE_USER.name())
                .antMatchers("/articles/**", "/items/**")
                .hasAnyAuthority(RoleNameEnumDTO.CUSTOMER_USER.name(),
                        RoleNameEnumDTO.SALE_USER.name())
                .antMatchers("/feedback")
                .hasAnyAuthority(RoleNameEnumDTO.ADMINISTRATOR.name(),
                        RoleNameEnumDTO.CUSTOMER_USER.name(),
                        RoleNameEnumDTO.SALE_USER.name())
                .antMatchers("/login", "/403", "/logout")
                .permitAll()
                .and()
                .formLogin()
                .permitAll()
                .successForwardUrl("/feedback")
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new LoginAccessDeniedHandler();
    }

}
