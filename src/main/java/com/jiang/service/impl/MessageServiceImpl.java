package com.jiang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.dao.MessageDO;
import com.jiang.mapper.MessageMapper;
import com.jiang.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * @author SmilingSea
 * 2024/4/5
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageDO> implements MessageService {
}
