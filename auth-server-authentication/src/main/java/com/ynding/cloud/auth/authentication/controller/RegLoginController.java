package com.ynding.cloud.auth.authentication.controller;

import com.ynding.cloud.auth.authentication.domin.AccessToken;
import com.ynding.cloud.auth.authentication.entity.User;
import com.ynding.cloud.auth.authentication.service.UserService;
import com.ynding.cloud.auth.authentication.utils.Md5Util;
import com.ynding.cloud.common.model.bo.ResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ynding
 */
@Slf4j
@RestController
@Api(value = "RegLogin", tags = {"登录注册-Controller"})
public class RegLoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * tokenHead
     */
    private final static String tokenHead = "Bearer ";


    @RequestMapping("/login_p")
    public ModelAndView login(ModelAndView m) {

        m.setViewName("login_p");
        return m;
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录", produces = "application/json")
    public ResponseBean refreshAndGetAuthenticationToken(
            @RequestBody User user) throws AuthenticationException {

        String username = user.getUsername();
        String password = user.getPassword();
        return ResponseBean.ok(generateToken(username, password));
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册", produces = "application/json")
    public ResponseBean register(
            @RequestBody User user) throws AuthenticationException {

        String username = user.getUsername();
        String password = user.getPassword();
        return userService.userReg(username,password);
    }


    /**
     * 登陆与授权.
     *
     * @param username .
     * @param password .
     * @return
     */
    private String generateToken(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userService.loadUserByUsername(username);
        // 持久化的redis
        String token = tokenHead + Md5Util.stringToMD5(userDetails.getUsername());
        redisTemplate.opsForValue().set(token, userDetails.getUsername());
        return token;
    }

    /**
     * 获取token（不可用）
     *
     * @param username .
     * @param password .
     * @return
     */
    private String getToken(String username, String password) {
        //获取token的url
        String tokenUrl = "http://localhost:10402/oauth/token";

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userService.loadUserByUsername(username);
        // 持久化的redis
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "123456");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 设置请求体
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", "password");
        map.add("scope", "read write");
        // 用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<AccessToken> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, AccessToken.class);
        String token = response.getBody().getAccess_token();
        redisTemplate.opsForValue().set(token, userDetails.getUsername());
        return token;
    }


}
