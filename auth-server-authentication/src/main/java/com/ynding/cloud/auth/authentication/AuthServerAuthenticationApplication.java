package com.ynding.cloud.auth.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * 认证服务器
 * @author ynding
 * @version 2020/09/03
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AuthServerAuthenticationApplication {

    /**
     * passwordEncoder 配置在config类里报错循环依赖，暂时将其写在启动类里，不报错
     * Spring 对密码加密的封装
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerAuthenticationApplication.class, args);
    }

}
