package com.jiang.domain.dto;

import lombok.Data;

/**
 * 消息数据传输对象
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
