package com.jiang.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

/**
 * @author SmilingSea
 * 2024/3/31
 */

@Data
@TableName("contacts")
public class ContactsDO {
    /**
     * 联系人关系ID（主键）
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 联系人ID
     */
    private Long contactId;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public ContactsDO(Long userId, Long contactId) {
        this.userId = userId;
        this.contactId = contactId;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
}
