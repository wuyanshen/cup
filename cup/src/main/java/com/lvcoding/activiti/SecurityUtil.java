/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.activiti;

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
