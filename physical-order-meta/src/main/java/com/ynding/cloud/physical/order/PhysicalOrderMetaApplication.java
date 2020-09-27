package com.ynding.cloud.physical.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 订单服务-资源服务器
 * @author ynding
 * @version 2020/09/09
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PhysicalOrderMetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhysicalOrderMetaApplication.class, args);
    }

}
