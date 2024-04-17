package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.annotation.TokenRequired;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.common.ResultWithToken;
import com.jiang.domain.dao.UserDO;
import com.jiang.domain.dto.UserDTO;
import com.jiang.mapper.UserMapper;
import com.jiang.service.UserService;

import com.jiang.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author SmilingSea
 * 2024/3/26
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     * @param userDO
     * @return
     */
    @Override
    public Result<String> register(UserDO userDO) {
        // 使用MD5进行加密密码
        userDO.setPassword(DigestUtils.md5DigestAsHex(userDO.getPassword().getBytes()));

        // 检查用户名是否重复
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, userDO.getUsername());
        if (userService.getOne(queryWrapper) != null) {
            return Result.error("用户已存在...");
        }
        userDO.setAuthority("0");

        userDO.setCreatedAt(LocalDateTime.now());
        userDO.setUpdatedAt(LocalDateTime.now());


        userService.save(userDO);

        return Result.success("注册成功！");
    }


    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public ResultWithToken<Map<String,Object>> login(UserDO user) {
        String username = user.getUsername();
        String password = user.getPassword();
        // 转换密码
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据用户名name查询数据库
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, username);
        UserDO usr = userService.getOne(queryWrapper);

        //如果没有查询到则返回失败
        if (usr == null) {
            return ResultWithToken.error("登录失败");
        }

        //比对密码，如果不一致则返回登录失败
        if (!usr.getPassword().equals(password)) {
            return ResultWithToken.error("登录失败");
        }

        // 获取token
        String token = JWTUtils.getToken(usr);
        Long id = JWTUtils.getIdByToken(token);

        //将token和id存入reids

        redisTemplate.opsForValue().set(id.toString(), token, 7, TimeUnit.DAYS);

        Map<String, Object> data = new HashMap<>();
        data.put("token",token);
        data.put("id",JWTUtils.getIdByToken(token));


        //登录成功，返回token
        return ResultWithToken.success(data ,"登录成功！");
    }


    /**
     * 根据id查询用户信息（不包含隐私）
     * @param token
     * @param id
     * @return
     */
    @TokenRequired
    @Override
    public ResultWithData<UserDTO> profile(String token, Long id) {
        // 根据id查找用户信息
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getId, id);
        UserDO user = userService.getOne(queryWrapper);
        UserDTO data = new UserDTO(user.getId(), user.getUsername(), user.getNickname(), user.getEmail(), user.getAuthority());
        return ResultWithData.success(data,"查询成功！");
    }

    /**
     * 根据token查询用户信息（包含隐私）
     * @param token
     * @return
     */
    @Override
    public ResultWithData<UserDO> profile(String token) {
        // 根据token获得id
        Long id = JWTUtils.getIdByToken(token);
        UserDO user = userService.getById(id);

        return ResultWithData.success(user,"查询成功！");
    }
}
