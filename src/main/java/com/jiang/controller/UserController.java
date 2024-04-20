package com.jiang.controller;


import com.jiang.annotation.TokenRequired;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.common.ResultWithToken;
import com.jiang.domain.dao.UserDO;
import com.jiang.domain.dto.UserDTO;
import com.jiang.service.BanService;
import com.jiang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
*@author SmilingSea
*2024/3/25
*/

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController  {

    private final UserService userService;

    private final BanService banService;

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
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResultWithToken<Map<String,Object>> login(@RequestBody UserDO user){
        return userService.login(user);
    }

    @GetMapping("/profile/{id}")
    public ResultWithData<UserDTO> profile(@RequestHeader String token, @PathVariable Long id){
        return userService.profile(token,id);
    }

    /**
     * 通过token查询个人信息
     * @param token
     * @return Result
     */
    @TokenRequired
    @GetMapping("/profile")
    public ResultWithData<UserDO> profile(@RequestHeader String token){
        return userService.profile(token);
    }

    /**
     * 屏蔽用户接口
     * @param token
     * @param bannerId
     * @return
     */
    @TokenRequired
    @PostMapping("/ban/{bannerId}")
    public Result<String> ban(@RequestHeader String token, @PathVariable("bannerId") Long bannerId){
        return banService.ban(token, bannerId);
    }

    /**
     * 解除屏蔽接口
     * @param token
     * @param bannerId
     * @return
     */
    @TokenRequired
    @DeleteMapping("/unban/{bannerId}")
    public Result<String> unBan(@RequestHeader String token, @PathVariable("bannerId") Long bannerId){
        return banService.unBan(token, bannerId);
    }
}
