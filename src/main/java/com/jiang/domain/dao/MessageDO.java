package com.jiang.domain.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jiang.domain.enums.MessageType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息实体类
 * @author SmilingSea
 * 2024/4/5
 */
@TableName("messages")
@Data
public class MessageDO {
    /**
     * 消息 id
     */
    @TableId
    private Long id;
    /**
     * 会话id
     */
    private Long conversationId;
    /**
     * 发送人id
     */
    private Long senderId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息类型
     */
    private MessageType type;
    /**
     * 发送时间
     */
    private LocalDateTime sentAt;
    /**
     * 接收时间
     */
    private LocalDateTime receivedAt;

    public MessageDO(Long id, Long conversationId, Long senderId, String content, MessageType type, LocalDateTime sentAt, LocalDateTime receivedAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
        this.type = type;
        this.sentAt = sentAt;
        this.receivedAt = receivedAt;
    }
}
