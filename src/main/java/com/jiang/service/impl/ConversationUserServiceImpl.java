package com.jiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.dao.ConversationUser;
import com.jiang.mapper.ConversationUserMapper;
import com.jiang.service.ConversationUserService;
import org.springframework.stereotype.Service;

/**
 * @author SmilingSea
 * 2024/4/5
 */
@Service
public class ConversationUserServiceImpl extends ServiceImpl<ConversationUserMapper, ConversationUser> implements ConversationUserService {
}
