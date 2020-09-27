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

package com.ynding.cloud.physical.order.controller;

import com.ynding.cloud.physical.order.model.OrderInfo;
import com.ynding.cloud.physical.order.model.PriceInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> OrderController</p>
 *
 * @author dyn
 * @version 2020/9/27
 * @since JDK 1.8
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@Api(value = "Order", tags = {"Order-Controller"})
public class OrderController {

    /**
     * 会从请求的上下文里拿到令牌，放到请求头里，发出去。
     */
    @Autowired
    private OAuth2RestTemplate restTemplate;

    /**
     * 创建订单
     */
    //注解生效需在启动类配置@EnableGlobalMethodSecurity(prePostEnabled = true)
    //@PreAuthorize("#oauth2.hasScope('write')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info, @AuthenticationPrincipal String username) {
        log.info("获取到username = {}", username);
        //查询价格
        PriceInfo price = new PriceInfo();
        log.info("price is " + price.getPrice());
        return info;
    }


    @GetMapping("/{id}")
    public OrderInfo getInfo(@PathVariable Long id, @RequestHeader String username) {
        log.info("getInfo: id is " + id + " , and username is " + username);
        OrderInfo info = new OrderInfo();
        info.setId(id);
        info.setProductId(id * 10);
        return info;
    }
}
