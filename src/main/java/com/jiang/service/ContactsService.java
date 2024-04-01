package com.jiang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.dao.ContactsDO;
import com.jiang.dao.UserDO;

import java.util.List;

/**
 * @author SmilingSea
 * 2024/3/31
 */
public interface ContactsService extends IService<ContactsDO> {
    Result<String> add(String token, UserDO user) ;

    Result<String> remove(String token, Long contactId);

    ResultWithData<List<Long>> getContacts(String token);

}
