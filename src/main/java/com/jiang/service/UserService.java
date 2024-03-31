package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.common.ResultWithToken;
import com.jiang.dao.UserDO;

/**
 * @author SmilingSea
 * 2024/3/26
 */

public interface UserService extends IService<UserDO> {
    Result<String> register(UserDO userDO);

    ResultWithToken<String> login(String name, String password);

}
