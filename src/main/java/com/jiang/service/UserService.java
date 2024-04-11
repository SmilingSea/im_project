package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.common.ResultWithToken;
import com.jiang.dao.UserDO;
import com.jiang.dto.UserDTO;

/**
 * @author SmilingSea
 * 2024/3/26
 */

public interface UserService extends IService<UserDO> {
    Result<String> register(UserDO userDO);

    ResultWithToken<String> login(UserDO user);

    ResultWithData<UserDTO> profile(String token, Long id);
}
