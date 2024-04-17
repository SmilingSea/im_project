package com.jiang.controller;

import com.jiang.aop.TokenRequired;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SmilingSea
 * 2024/4/2
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/conversation")
public class ConversationController {
    private final ConversationService conversationService;

    /**
     * 创建会话
     * @param token
     * @param contactId 联系人id
     * @return Result
     */
    @TokenRequired
    @PostMapping("/create")
    public Result<String> create(@RequestHeader String token, @RequestParam List<Long> contactId){
        return conversationService.create(token, contactId);
    }

    /**
     * 获取会话成员
     * @param token
     * @param conversationId 会话id
     * @return
     */
    @TokenRequired
    @GetMapping("/members")
    public ResultWithData<List<Long>> getMembers(@RequestHeader String token, @RequestParam Long conversationId){
        return conversationService.getMembers(token, conversationId);
    }

    /**
     * 获取会话列表
     * @param token
     * @return
     */
    @TokenRequired
    @GetMapping("/getlist")
    public ResultWithData<List<Long>> getConversations(@RequestHeader String token){
        return conversationService.getConversations(token);
    }
}
