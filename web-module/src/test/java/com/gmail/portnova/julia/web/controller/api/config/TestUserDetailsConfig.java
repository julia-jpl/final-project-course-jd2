package com.gmail.portnova.julia.web.controller.api.config;

import com.gmail.portnova.julia.service.model.RoleNameEnumDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class TestUserDetailsConfig {

    @Bean
    public UserDetailsService getUserDetails() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                switch (username) {
                    case "api":
                        return getUserRestApiDetails();
                    case "admin":
                        return getUserAdminDetails();
                    case "customer":
                        return getUserCustomDetails();
                    case "sale":
                        return  getUserSaleDetails();
                    default:
                        throw new UsernameNotFoundException(String.format("User with username %s was not found", username));
                }
            }

            private UserDetails getUserSaleDetails() {
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        String role = RoleNameEnumDTO.SALE_USER.name();
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                        return Collections.singletonList(grantedAuthority);
                    }

                    @Override
                    public String getPassword() {
                        return new BCryptPasswordEncoder().encode("1234");
                    }

                    @Override
                    public String getUsername() {
                        return "sale";
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
            }

            private UserDetails getUserCustomDetails() {
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        String role = RoleNameEnumDTO.CUSTOMER_USER.name();
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                        return Collections.singletonList(grantedAuthority);
                    }

                    @Override
                    public String getPassword() {
                        return new BCryptPasswordEncoder().encode("1234");
                    }

                    @Override
                    public String getUsername() {
                        return "customer";
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
            }

            private UserDetails getUserAdminDetails() {
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        String role = RoleNameEnumDTO.ADMINISTRATOR.name();
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                        return Collections.singletonList(grantedAuthority);
                    }

                    @Override
                    public String getPassword() {
                        return new BCryptPasswordEncoder().encode("1234");
                    }

                    @Override
                    public String getUsername() {
                        return "admin";
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
            }

            private UserDetails getUserRestApiDetails() {
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        String role = RoleNameEnumDTO.SECURE_REST_API.name();
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                        return Collections.singletonList(grantedAuthority);
                    }

                    @Override
                    public String getPassword() {
                        return new BCryptPasswordEncoder().encode("1234");
                    }

                    @Override
                    public String getUsername() {
                        return "api";
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
            }
        };
    }

}
