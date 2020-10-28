package com.ynding.cloud.physical.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 订单服务-资源服务器
 * @author ynding
 * @version 2020/09/09
 */
@SpringBootApplication
@EnableDiscoveryClient
//启用权限表达式，使注解@PreAuthorize生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PhysicalOrderMetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhysicalOrderMetaApplication.class, args);
    }

}
