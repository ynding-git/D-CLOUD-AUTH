package com.ynding.cloud.physical.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * <p> 资源服务器配置</p>
 *
 * @author dyn
 * @version 2020/10/28
 * @since JDK 1.8
 */
@Slf4j
@Configuration
@EnableResourceServer
public class OrderResourceConfig extends ResourceServerConfigurerAdapter {

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

    /**
     * 如果不做配置的话，auth2不会做权限控制，经过网管可以访问本服务，其他服务也可以访问，加了配置之后，resource_ids必须包含order-server才可访问本服务。
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //配置资源服务器的id，“现在我就是资源服务器 order-server！！！”
        resources.resourceId("order-server");
    }

    /**
     * 资源服务器保护的端点，优先级高于 spring security 的配置。不配置的话，第三方应用通过认证后，可访问本服务所有接口
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("ResourceServerConfig 中配置 HttpSecurity对象执行");
        // 只有/order 端点作为资源服务器的资源
        http.requestMatchers().antMatchers("/order/**")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

}
