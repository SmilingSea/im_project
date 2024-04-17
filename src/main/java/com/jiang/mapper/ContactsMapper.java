package com.jiang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.domain.dao.ContactsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author SmilingSea
 * 2024/3/31
 */
@Mapper
public interface ContactsMapper extends BaseMapper<ContactsDO> {
}
