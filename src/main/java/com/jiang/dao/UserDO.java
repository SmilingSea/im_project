package com.jiang.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
*@author SmilingSea
*2024/3/25
*/
@Data
@TableName("user")
public class UserDO {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String authority;
    private Date createdAt;
    private Date updatedAt;
}
