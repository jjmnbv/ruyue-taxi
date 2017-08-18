package com.xxkj.passenger.wechat.mapper;

import com.szyciov.org.entity.OrgUser;
import com.xxkj.passenger.wechat.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserAccessMapper {
    Map<String,Object> getUserTokenByUserId(Map<String, Object> pp);

    User getUserById(String id);

    OrgUser getUser4Org(String account);

    User getUser4Op(String account);

    User getUserByOpenId(String openId);

    void bindUserWithOpenId(User user);

    void createOrUpdateUsertoken(Map<String, Object> tokeninfo);

    void addLog4Pe(Map<String, Object> loginfo);

    /**
     * 删除验证码
     * */
    void deleteSMSCode(Map<String, Object> params);

    /**
     * 获取验证码
     * */
    Map<String, Object> getSMSInfo(Map<String, Object> params);
}
