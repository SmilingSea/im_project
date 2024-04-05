package com.jiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.dao.MessageDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author SmilingSea
 * 2024/4/5
 */
@Mapper
public interface MessageMapper extends BaseMapper<MessageDO> {
}
