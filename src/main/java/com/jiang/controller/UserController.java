package com.jiang.controller;


import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.common.ResultWithToken;
import com.jiang.dao.UserDO;
import com.jiang.dto.UserDTO;
import com.jiang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


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
    public ResultWithToken<String> login(@RequestBody UserDO user){
        return userService.login(user);
    }

    @GetMapping("/profile/{id}")
    public ResultWithData<UserDTO> profile(@RequestHeader String token, @PathVariable Long id){
        return userService.profile(token,id);
    }

}
