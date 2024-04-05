package com.jiang.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author SmilingSea
 * 2024/4/2
 */
@Data
@TableName("conversation_user")
public class ConversationUser {
    private Long conversationId;
    private Long userId;

    public ConversationUser(Long conversationId, Long userId) {
        this.conversationId = conversationId;
        this.userId = userId;
    }
}
