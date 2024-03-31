package com.jiang.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
*@author SmilingSea
*2024/3/25
*/
@Data
@TableName("user")
public class UserDO {
    @TableId
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String authority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
