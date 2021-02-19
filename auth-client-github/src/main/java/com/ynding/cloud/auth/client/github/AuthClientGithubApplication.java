package com.ynding.cloud.auth.client.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 基于github oauth服务 的客户端
 * @author ynding
 * @version 2021/02/19
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AuthClientGithubApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthClientGithubApplication.class, args);
    }

}
