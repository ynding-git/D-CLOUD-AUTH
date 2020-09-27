package com.ynding.cloud.physical.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 订单服务-资源服务器
 * @author ynding
 * @version 2020/09/09
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@Configuration
public class PhysicalOrderMetaApplication {

    /**
     * 声名OAuth2RestTemplate
     * 会从请求的上下文里拿到令牌，放到请求头里，发出去。需要两个参数，springboot会自动出入进来
     * @param resource
     * @param context
     * @return
     */
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context){
        return new OAuth2RestTemplate(resource,context);
    }

    public static void main(String[] args) {
        SpringApplication.run(PhysicalOrderMetaApplication.class, args);
    }

}
