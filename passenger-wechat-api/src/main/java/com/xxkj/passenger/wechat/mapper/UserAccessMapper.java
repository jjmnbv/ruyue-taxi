package com.xxkj.passenger.wechat.mapper;

import com.szyciov.entity.PubSmsToken;
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

    /**
     * 手机号是否已注册
     * */
//    boolean hasRegister(String phone);

    void bindUserWithOpenId(User user);

    void registerPeUser(User peuser);

    void createOrUpdateUsertoken(Map<String, Object> tokeninfo);

    void addLog4Pe(Map<String, Object> loginfo);

    /**
     * 删除验证码
     * */
    void deleteSMSCode(Map<String, Object> params);

    /**
     * 获取验证码
     * */
    PubSmsToken getSMSInfo(Map<String, Object> params);
}
