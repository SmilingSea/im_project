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
     * 发消息的用户id
     */
    private Long userId;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     */
    private MessageType type;

}
