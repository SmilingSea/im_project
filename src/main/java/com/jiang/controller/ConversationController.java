package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.service.ContactsService;
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


    @PostMapping("/create")
    public Result<String> create(@RequestHeader String token, @RequestParam List<Long> contactId){
        return conversationService.create(token, contactId);
    }

    @GetMapping("/members")
    public ResultWithData<List<Long>> getMembers(@RequestHeader String token, @RequestParam Long conversationId){
        return conversationService.getMembers(token, conversationId);
    }

    @GetMapping("/getlist")
    public ResultWithData<List<Long>> getConversations(@RequestHeader String token){
        return conversationService.getConversations(token);
    }
}
