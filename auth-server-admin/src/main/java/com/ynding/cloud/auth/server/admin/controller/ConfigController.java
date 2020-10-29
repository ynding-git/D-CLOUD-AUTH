package com.ynding.cloud.auth.server.admin.controller;


import com.ynding.cloud.auth.server.admin.data.MenuRepository;
import com.ynding.cloud.auth.server.admin.data.UserRepository;
import com.ynding.cloud.auth.server.admin.entity.Menu;
import com.ynding.cloud.auth.server.admin.entity.User;
import com.ynding.cloud.auth.server.admin.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 这是一个只要登录就能访问的Controller
 * 主要用来获取一些配置信息
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    MenuService menuService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    MenuRepository menuRepository;


    @GetMapping("/sysMenu")
    public List<Menu> sysMenu() {

        return menuRepository.findAll();
    }

    @GetMapping("/user")
    public User currentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
