package com.jiang.service.impl;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.dao.BanDO;
import com.jiang.mapper.BanMapper;
import com.jiang.service.BanService;
import com.jiang.utils.JWTUtils;
import org.springframework.stereotype.Service;

/**
 * @author SmilingSea
 * 2024/4/13
 */
@Service
public class BanServiceImpl extends ServiceImpl<BanMapper, BanDO> implements BanService {


    @Override
    public Result<String> ban(String token, Long bannerId) {
        Long id = JWTUtils.getIdByToken(token);
        save(new BanDO(id,bannerId));
        return Result.success("屏蔽成功！");
    }
}
