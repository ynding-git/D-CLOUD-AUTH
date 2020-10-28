/*
 * The Hefei JingTong RDC(Research and Development Centre) Group.
 * __________________
 *
 *    Copyright 2015-2020
 *    All Rights Reserved.
 *
 *    NOTICE:  All information contained herein is, and remains
 *    the property of JingTong Company and its suppliers,
 *    if any.
 */

package com.ynding.cloud.physical.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * <p> </p>
 *
 * @author dyn
 * @version 2020/10/28
 * @since JDK 1.8
 */
@Configuration
@EnableResourceServer
public class OrderSecurityConfig extends ResourceServerConfigurerAdapter {

    /**
     * 声名OAuth2RestTemplate
     * 会从请求的上下文里拿到jwt令牌，放到请求头里，发出去。需要两个参数，springboot会自动出入进来
     * @param resource
     * @param context
     * @return
     */
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context){
        return new OAuth2RestTemplate(resource,context);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //配置资源服务器的id，“现在我就是资源服务器 order-server！！！”
        resources.resourceId("order-server");
    }

}
