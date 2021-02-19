package com.ynding.cloud.auth.client.github.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * HelloController
 * @author ynding
 * @version 2021/2/19
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Principal principal){
        return "hello, " + principal.getName();
    }
}
