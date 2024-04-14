package com.jiang.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author SmilingSea
 * 2024/4/13
 */
@Data
@TableName("ban")
public class BanDO {
    private Long userId;

    private Long bannerId;

    public BanDO(Long userId, Long bannerId) {
        this.userId = userId;
        this.bannerId = bannerId;
    }
}
