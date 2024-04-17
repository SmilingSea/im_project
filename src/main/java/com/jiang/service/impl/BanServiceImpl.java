package com.jiang.service.impl;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.domain.dao.BanDO;
import com.jiang.mapper.BanMapper;
import com.jiang.service.BanService;
import com.jiang.utils.JWTUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author SmilingSea
 * 2024/4/13
 */
@Service
public class BanServiceImpl extends ServiceImpl<BanMapper, BanDO> implements BanService {


    /**
     * 屏蔽用户
     *
     * @param token
     * @param bannerId
     * @return
     */
    @Override
    public Result<String> ban(String token, Long bannerId) {
        Long id = JWTUtils.getIdByToken(token);
        LambdaQueryWrapper<BanDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BanDO::getBannerId, bannerId).eq(BanDO::getUserId, id);
        if (getOne(queryWrapper) != null) {
            return Result.error("你已经屏蔽该用户");
        }
        if (bannerId.equals(id)) {
            return Result.error("你不能屏蔽自己。");
        }

        save(new BanDO(id, bannerId));
        return Result.success("屏蔽成功！");
    }

    @Override
    public Result<String> unBan(String token, Long bannerId) {
        Long id = JWTUtils.getIdByToken(token);
        LambdaQueryWrapper<BanDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BanDO::getBannerId, bannerId).eq(BanDO::getUserId, id);

        if (getOne(queryWrapper) == null) {
            return Result.error("该用户未被屏蔽");
        }
        remove(queryWrapper);


        return Result.success("移除成功！");
    }
}
