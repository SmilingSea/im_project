package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.domain.dao.BanDO;

/**
 * @author SmilingSea
 * 2024/4/13
 */
public interface BanService extends IService<BanDO> {
    Result<String> ban(String token, Long bannerId);
}
