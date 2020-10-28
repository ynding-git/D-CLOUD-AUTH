package com.ynding.cloud.auth.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author ynding
 * @version 2020/9/9
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // TODO 验证用户
        return User.withUsername(username)
                .password(passwordEncoder.encode("123456"))
                //权限
                .authorities("ROLE_ADMIN")
                .build();
    }
}
