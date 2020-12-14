package com.ynding.cloud.auth.server.admin.controller;


import com.ynding.cloud.auth.server.admin.entity.User;
import com.ynding.cloud.auth.server.admin.service.UserService;
import com.ynding.cloud.common.model.bo.Query;
import com.ynding.cloud.common.model.bo.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author ynding
 */
@RestController
@RequestMapping("/user")
@Api(value="User",tags={"User-Controller"})
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    @ApiOperation(value = "查询列表", produces = "application/json")
    public ResponseBean findList(@RequestParam Map<String, Object> params){

        Query query = new Query(params);
        List<User> users = userService.findList(query);

        return ResponseBean.ok(users);
    }

    @GetMapping("/top")
    @ApiOperation(value = "查询前几个用户", produces = "application/json")
    public ResponseBean findTop(@RequestParam Map<String, Object> params){

        Query query = new Query(params);
        List<User> users = userService.findTop(query);

        return ResponseBean.ok(users);
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询", produces = "application/json")
    public ResponseBean pageList(@RequestParam Map<String, Object> params){
        Query query = new Query(params);
        Page<User> users = userService.pageList(query);

        return ResponseBean.ok(users);
    }
}
