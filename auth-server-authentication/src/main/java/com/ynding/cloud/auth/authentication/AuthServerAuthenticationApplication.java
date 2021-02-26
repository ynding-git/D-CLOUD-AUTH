package com.ynding.cloud.auth.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

/**
 * 认证服务器（另外把它注册为资源服务器，以便可以调用其中的接口，否则报403 Forbidden错误，无法调用其中的资源）
 * 意思是从认证服务器获取的token可以有权限调取对应资源服务器的资源，认证服务器除外，除非加上@EnableResourceServer
 * @author ynding
 * @version 2020/09/03
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@EnableAuthorizationServer
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

    /**
     * 配置在config类里报错循环依赖
     * redis 连接工厂，session 共享用
     * @return
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        return new LettuceConnectionFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerAuthenticationApplication.class, args);
    }

}
