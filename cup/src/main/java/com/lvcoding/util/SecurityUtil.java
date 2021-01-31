package com.lvcoding.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class SecurityUtil {

    @Autowired
    @Qualifier("commonUserDetailServiceImpl")
    private UserDetailsService userDetailsService;

    public void loginAs(String username) {
        final UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new IllegalStateException("User " + username + "doesn't exist,please provide a valid username");
        }
        log.info("> login as: " + username);
        SecurityContextHolder.setContext(
                new SecurityContextImpl(
                        new Authentication() {
                            @Override
                            public Collection<? extends GrantedAuthority> getAuthorities() {
                                return user.getAuthorities();
                            }

                            @Override
                            public Object getCredentials() {
                                return user.getPassword();
                            }

                            @Override
                            public Object getDetails() {
                                return user;
                            }

                            @Override
                            public Object getPrincipal() {
                                return user;
                            }

                            @Override
                            public boolean isAuthenticated() {
                                return true;
                            }

                            @Override
                            public void setAuthenticated(boolean b) throws IllegalArgumentException {
                            }

                            @Override
                            public String getName() {
                                return user.getUsername();
                            }
                        }
                )
        );
        org.activiti.engine.impl.identity.Authentication.setAuthenticatedUserId(username);
    }
}
