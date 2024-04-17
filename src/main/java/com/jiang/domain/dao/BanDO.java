package com.jiang.domain.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 屏蔽表实体类
 * @author SmilingSea
 * 2024/4/13
 */
@Data
@TableName("ban")
public class BanDO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 屏蔽用户id
     */
    private Long bannerId;

    public BanDO(Long userId, Long bannerId) {
        this.userId = userId;
        this.bannerId = bannerId;
    }
}
