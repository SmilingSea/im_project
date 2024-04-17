package com.jiang.domain.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jiang.domain.enums.ConversationType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 会话实体类
 * @author SmilingSea
 * 2024/4/2
 */
@Data
@TableName("conversations")
public class ConversationDO {
    /**
     * 会话id（主键）
     */
    @TableId
    private Long id;
    /**
     * 会话类型（单聊/群聊）
     */
    private ConversationType type;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
