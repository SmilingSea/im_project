package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.dao.UserDO;
import com.jiang.mapper.UserMapper;
import com.jiang.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author SmilingSea
 * 2024/3/26
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    private UserService userService;

//    @Override
//    public Result<HashMap<String, Object>> save(HttpServletRequest request, UserDO userDO) {
//        // 使用MD5进行加密密码
//        userDO.setPassword(DigestUtils.md5DigestAsHex(userDO.getPassword().getBytes()));
//
//        // 检查用户名是否重复
//        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(UserDO::getUsername, userDO.getUsername());
//        if (userService.getOne(queryWrapper) != null){
//            return Result.error("用户已存在...");
//        }
//        userDO.setAuthority("0");
//        userDO.setCreatedAt(LocalDateTime.now());
//        userDO.setUpdatedAt(LocalDateTime.now());
//
//
//
//        userService.save(userDO);
//
//        return Result.success("注册成功！");
//    }
//
//    @Override
//    public UserDO getByUsername(String username) {
//        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(UserDO::getUsername, username);
//        return userService.getOne(queryWrapper);
//    }


}
