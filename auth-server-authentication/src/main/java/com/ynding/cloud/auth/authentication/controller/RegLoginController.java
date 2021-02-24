package com.ynding.cloud.auth.authentication.controller;

import com.ynding.cloud.auth.authentication.entity.User;
import com.ynding.cloud.auth.authentication.service.UserService;
import com.ynding.cloud.auth.authentication.utils.Md5Util;
import com.ynding.cloud.common.model.bo.ResponseBean;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 获取token的url
     */
    private final static String tokenUrl = "http://localhost:10402/oauth/token";
    /**
     * 客户端id（用于获取token）
     */
    private final static String clientId = "admin";
    /**
     * 客户端secret（用于获取token）
     */
    private final static String clientSecret = "123456";

    @RequestMapping("/login_p")
    public ModelAndView login(ModelAndView m) {

        m.setViewName("login_p");
        return m;
    }

    @GetMapping("/login")
    public ResponseBean testToke(@AuthenticationPrincipal String username, Authentication authentication) {
        log.info("getInfo: username is " + username);
        authentication.getCredentials();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        String jwtToken = details.getTokenValue();
        log.info("token:{}", jwtToken);
        return ResponseBean.ok(username);
    }

    @PostMapping("/login")
    public ResponseBean refreshAndGetAuthenticationToken(
            @RequestBody User user) throws AuthenticationException {

        String username = user.getUsername();
        String password = user.getPassword();
        return ResponseBean.ok(getToken(username, password));
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
     * 获取token
     *
     * @param username .
     * @param password .
     * @return
     */
    private String getToken(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userService.loadUserByUsername(username);
        // 持久化的redis
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 设置请求体
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", "password");
        map.add("scope", "read write");
        // 用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
        String token = response.getBody();
        redisTemplate.opsForValue().set(token, userDetails.getUsername());
        return token;
    }


}
