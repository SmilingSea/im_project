package com.jiang.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.dao.MessageDO;
import com.jiang.service.ConversationService;
import com.jiang.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author SmilingSea
 * 2024/4/12
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;


    @PostMapping("/image")
    public ResultWithData<String> sendPicture(@RequestHeader String token, @RequestParam("image") MultipartFile file ) throws IOException {
        return messageService.sendPicture(token, file);
    }

    @GetMapping("/history/{conversation_id}")
    public ResultWithData<List<String>> history(@RequestHeader String token, @PathVariable("conversation_id") Long conversationId) throws JsonProcessingException {
        return messageService.history(token , conversationId);
    }


}
