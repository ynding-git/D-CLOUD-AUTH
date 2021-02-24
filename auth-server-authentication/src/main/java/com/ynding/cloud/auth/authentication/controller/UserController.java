package com.ynding.cloud.auth.authentication.controller;


import com.ynding.cloud.auth.authentication.entity.User;
import com.ynding.cloud.auth.authentication.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ynding
 */
@RestController
@RequestMapping("/user")
@Api(value="User",tags={"User-Controller"})
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/loadUserByUsername")
    @ApiOperation(value = "根据用户名称查找用户", produces = "application/json")
    public UserDetails loadUserByUsername(String s) {
        User user = (User) userService.loadUserByUsername(s);
        return user;
    }

}
