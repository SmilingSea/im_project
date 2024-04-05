package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.dao.ConversationDO;

import java.util.List;

/**
 * @author SmilingSea
 * 2024/4/2
 */
public interface ConversationService extends IService<ConversationDO> {
    Result<String> create(String token, List<Long> contactId);

    ResultWithData<List<Long>> getMembers(String token, Long conversationId);

    ResultWithData<List<Long>> getConversations(String token);

}
