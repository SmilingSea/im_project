package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiang.common.ResultWithData;
import com.jiang.domain.dao.MessageDO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author SmilingSea
 * 2024/4/5
 */
public interface MessageService extends IService<MessageDO> {

    ResultWithData<String> sendPicture(String token ,MultipartFile file) throws IOException;

    ResultWithData<List<String>> history(String token, Long conversationId) throws JsonProcessingException;

}
