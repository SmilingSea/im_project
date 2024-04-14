package com.jiang.dto;

import com.jiang.dao.MessageType;
import lombok.Data;

/**
 * @author SmilingSea
 * 2024/4/5
 */
@Data
public class MessageDTO {
    /**
     * 会话Id
     */
    private Long conversationId;
    /**
     * 消息内容
     */
    private String content;

}
