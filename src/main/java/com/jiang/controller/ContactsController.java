package com.jiang.controller;

import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.dao.UserDO;
import com.jiang.service.ContactsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SmilingSea
 * 2024/3/31
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactsController {
    private final ContactsService contactsService;

    @PostMapping("/add")
    public Result<String> add(@RequestHeader String token, @RequestBody UserDO user){
        return contactsService.add(token, user);
    }

    @DeleteMapping("/remove/{id}")
    public Result<String> remove(@RequestHeader String token, @PathVariable Long id){
        return contactsService.remove(token, id);
    }

    @GetMapping("/list")
    public ResultWithData<List<Long>> getContacts(@RequestHeader String token){
        return contactsService.getContacts(token);
    }
}
