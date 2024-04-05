package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.dao.ConversationDO;
import com.jiang.dao.ConversationType;
import com.jiang.dao.ConversationUser;
import com.jiang.mapper.ConversationMapper;
import com.jiang.service.ConversationService;
import com.jiang.service.ConversationUserService;
import com.jiang.utils.JWTUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SmilingSea
 * 2024/4/2
 */
@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, ConversationDO> implements ConversationService {

    @Resource
    private ConversationService conversationService;

    @Resource
    private ConversationUserService conversationUserService;

    @Override
    public Result<String> create(String token, List<Long> contactId) {
        ConversationDO conversation = new ConversationDO();
        // 判断会话类型
        if (contactId.size() == 0){
            return Result.error("请选择与谁建立会话...");
        }else if(contactId.size() == 1){
            conversation.setType(ConversationType.SOLO);
        }else{
            conversation.setType(ConversationType.GROUP);
        }

        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setUpdatedAt(LocalDateTime.now());
        conversationService.save(conversation);

        Long conversationId = conversation.getId();

        for (Long id : contactId) {
            ConversationUser conversationUser = new ConversationUser(conversationId, id);
            conversationUserService.save(conversationUser);
        }

        ConversationUser conversationUser = new ConversationUser(conversationId, JWTUtils.getIdByToken(token));
        conversationUserService.save(conversationUser);

        return Result.success("创建成功！");
    }

    @Override
    public ResultWithData<List<Long>> getMembers(String token, Long conversationId) {
        LambdaQueryWrapper<ConversationUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConversationUser::getConversationId, conversationId);
        List<ConversationUser> list = conversationUserService.list(queryWrapper);

        List<Long> memberIds = new ArrayList<>();

        for (ConversationUser conversationUser : list) {
            memberIds.add(conversationUser.getUserId());
        }

        return ResultWithData.success(memberIds,"查询成功！");
    }

    @Override
    public ResultWithData<List<Long>> getConversations(String token) {
        Long userId = JWTUtils.getIdByToken(token);
        LambdaQueryWrapper<ConversationUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConversationUser::getUserId, userId);

        List<ConversationUser> list = conversationUserService.list();
        ArrayList<Long> conversationIds = new ArrayList<>();

        for (ConversationUser conversationUser : list) {
            Long conversationId = conversationUser.getConversationId();
            // 检查是否已经存在于列表中
            if (!conversationIds.contains(conversationId)) {
                conversationIds.add(conversationId);
            }
        }

        return ResultWithData.success(conversationIds,"查找会话成功！");
    }
}
