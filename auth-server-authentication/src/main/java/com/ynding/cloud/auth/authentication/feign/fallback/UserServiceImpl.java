package com.ynding.cloud.auth.authentication.feign.fallback;

import com.ynding.cloud.auth.authentication.feign.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * <p> 断路器</p>
 *
 * @author ynding
 * @version 2021/2/23
 **/
@Component
public class UserServiceImpl implements UserService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        return null;
    }
}
