package com.jiang.domain.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 会话——用户关系实体类
 * @author SmilingSea
 * 2024/4/13
 */
@Data
@TableName("conversation_user")
public class ConversationUser {
    /**
     * 会话id
     */
    private Long conversationId;

    /**
     * 用户id
     */
    private Long userId;


    public ConversationUser(Long conversationId, Long userId) {
        this.conversationId = conversationId;
        this.userId = userId;
    }
}
