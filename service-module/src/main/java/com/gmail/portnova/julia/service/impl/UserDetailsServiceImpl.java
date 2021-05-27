package com.gmail.portnova.julia.service.impl;

import com.gmail.portnova.julia.service.UserService;
import com.gmail.portnova.julia.service.model.UserDTO;
import com.gmail.portnova.julia.service.model.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.findUserByEmail(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User with username %s was not found", username));
        }
        return new UserLogin(user);
    }
}
