package com.jiang.domain.dto;

import lombok.Data;


/**
 * 用户数据传输对象
 * @author SmilingSea
 * 2024/3/31
 */
@Data
public class UserDTO {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户权限
     */
    private String authority;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String nickname, String email, String authority) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.authority = authority;
    }
}
