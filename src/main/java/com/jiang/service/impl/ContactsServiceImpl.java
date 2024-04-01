package com.jiang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.common.Result;
import com.jiang.common.ResultWithData;
import com.jiang.dao.ContactsDO;
import com.jiang.dao.UserDO;
import com.jiang.mapper.ContactsMapper;
import com.jiang.service.ContactsService;
import com.jiang.service.UserService;
import com.jiang.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author SmilingSea
 * 2024/3/31
 */
@Slf4j
@Service
public class ContactsServiceImpl extends ServiceImpl<ContactsMapper, ContactsDO> implements ContactsService {

    @Resource
    private UserService userService;

    @Resource
    private ContactsService contactsService;



    @Override
    public Result<String> add(String token, UserDO user) {
        Long userId = JWTUtils.getIdByToken(token);
        // 在数据库中查询该用户是否存在
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getId, user.getId());
        UserDO userDO = userService.getOne(queryWrapper);

        if (userDO == null){
            return Result.error("该用户不存在");
        }

        // 在contacts表中添加关系
        ContactsDO contactsDO = new ContactsDO(userId, user.getId());
        contactsService.save(contactsDO);
        return Result.success("添加联系人成功");
    }

    @Override
    public Result<String> remove(String token, Long id) {

        // 查询是否存在该联系人
        LambdaQueryWrapper<ContactsDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContactsDO::getId, id);
        ContactsDO contact = contactsService.getOne(queryWrapper);
        if (contact == null){
            return Result.error("该联系人不存在");
        }
        contactsService.removeById(id);

        return Result.success("删除成功");
    }

    @Override
    public ResultWithData<List<Long>> getContacts(String token) {
        List<Long> data = new ArrayList<>();
        Long id = JWTUtils.getIdByToken(token);
        LambdaQueryWrapper<ContactsDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContactsDO::getUserId, id);
        List<ContactsDO> list = contactsService.list(queryWrapper);
        for (ContactsDO contactsDO : list) {
            data.add(contactsDO.getContactId());
        }


        return ResultWithData.success(data, "查找成功！");
    }
}
