package com.jiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.dao.UserDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author SmilingSea
 * 2024/3/26
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
