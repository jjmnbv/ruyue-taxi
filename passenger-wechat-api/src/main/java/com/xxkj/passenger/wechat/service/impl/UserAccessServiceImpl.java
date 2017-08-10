package com.xxkj.passenger.wechat.service.impl;

import com.xxkj.passenger.wechat.entity.User;
import com.xxkj.passenger.wechat.mapper.UserAccessMapper;
import com.xxkj.passenger.wechat.service.UserAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lenovo23
 * @Title:UserAccessServiceImpl
 * @Package com.xxkj.passenger.wechat.service.impl
 * @Description
 * @date 2017/8/7
 * @Copyrigth 版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Service
public class UserAccessServiceImpl implements UserAccessService {

    @Autowired
    UserAccessMapper userAccessMapper;

    @Override
    public User getUserById(String id) {
        return userAccessMapper.getUserById(id);
    }

    @Override
    public boolean verifyOpenId(String openId) {
        User user = userAccessMapper.getUserByOpenId(openId);
        return user != null;
    }

    @Override
    public void bindUserWithOpenId(User user) {
        userAccessMapper.bindUserWithOpenId(user);
    }

}
