package com.jiang.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author SmilingSea
 * 2024/4/5
 */
@TableName("messages")
@Data
public class MessageDO {
    @TableId
    private Long id;
    private Long conversationId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private MessageType type;
    private LocalDateTime sentAt;
    private LocalDateTime receivedAt;
}
