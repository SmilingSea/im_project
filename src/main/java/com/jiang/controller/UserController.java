package com.jiang.controller;


import com.jiang.common.Result;
import com.jiang.common.ResultWithToken;
import com.jiang.dao.UserDO;
import com.jiang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import java.util.List;

/**
*@author SmilingSea
*2024/3/25
*/

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 用户注册接口
     * @param userDO 用户对象
     * @return result结果集
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserDO userDO){
        return userService.register(userDO);
    }

    /**
     * 用户登录接口
     * @param username springsecurity传入username
     * @param password springsecurity传入password
     * @return 带有token的result
     */
    @PostMapping("/login")
    public ResultWithToken<String> login(String username, String password){
        return userService.login(username, password);
    }

    @GetMapping("/profile")
    public



}
