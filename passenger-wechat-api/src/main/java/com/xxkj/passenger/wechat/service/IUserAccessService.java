package com.xxkj.passenger.wechat.service;

import com.szyciov.org.entity.OrgUser;
import com.xxkj.passenger.wechat.entity.User;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface IUserAccessService {
    User getUserById(String id);

    User getUserByOpenId(String openId);

    Map<String, Object> bindUserWithOpenId(User user);

    Map<String, Object> login(User user, String loginType) throws NoSuchAlgorithmException;
}
