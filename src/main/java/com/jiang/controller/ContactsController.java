package com.jiang.controller;

import com.jiang.annotation.TokenRequired;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.domain.dao.UserDO;
import com.jiang.service.ContactsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 联系人模块接口
 * @author SmilingSea
 * 2024/3/31
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactsController {
    private final ContactsService contactsService;

    /**
     * 添加联系人
     * @param token 用户token
     * @param user 联系人用户id
     * @return Result
     */
    @TokenRequired
    @PostMapping("/add")
    public Result<String> add(@RequestHeader String token, @RequestBody UserDO user){
        return contactsService.add(token, user);
    }

    /**
     * 删除联系人
     * @param token 用户token
     * @param contactId 联系人id
     * @return Result
     */
    @TokenRequired
    @DeleteMapping("/remove/{contactId}")
    public Result<String> remove(@RequestHeader String token, @PathVariable Long contactId){
        return contactsService.remove(token, contactId);
    }

    /**
     * 查看联系人列表
     * @param token
     * @return Result
     */
    @TokenRequired
    @GetMapping("/list")
    public ResultWithData<List<Long>> getContacts(@RequestHeader String token){
        return contactsService.getContacts(token);
    }
}
