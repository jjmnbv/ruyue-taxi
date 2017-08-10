package com.xxkj.passenger.wechat.service;

import com.xxkj.passenger.wechat.entity.User;

public interface UserAccessService {
    User getUserById(String id);

    boolean verifyOpenId(String openid);

    void bindUserWithOpenId(User user);
}
