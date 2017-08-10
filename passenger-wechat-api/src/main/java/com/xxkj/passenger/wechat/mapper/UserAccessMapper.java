package com.xxkj.passenger.wechat.mapper;

import com.xxkj.passenger.wechat.entity.User;

@org.apache.ibatis.annotations.Mapper
public interface UserAccessMapper {
    User getUserById(String id);

    User getUserByOpenId(String openId);

    void bindUserWithOpenId(User user);
}
