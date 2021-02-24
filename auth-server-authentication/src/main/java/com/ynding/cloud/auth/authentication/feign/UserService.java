package com.ynding.cloud.auth.authentication.feign;

import com.ynding.cloud.auth.authentication.feign.fallback.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p> 调取admin登录服务的userService</p>
 *
 * @author ynding
 * @version 2021/2/23
 **/
@FeignClient(value = "auth-server-admin", fallback = UserServiceImpl.class)
@Component
public interface UserService {

    @GetMapping("/user/loadUserByUsername")
    UserDetails loadUserByUsername(String username);
}
