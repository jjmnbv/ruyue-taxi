package com.xxkj.passenger.wechat.test;

import com.alibaba.fastjson.JSON;
import com.xxkj.passenger.wechat.Const;
import com.xxkj.passenger.wechat.entity.User;
import com.xxkj.passenger.wechat.service.IUserAccessService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.Cookie;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author lenovo23
 * @Title:UserTest
 * @Package com.xxkj.passenger.wechat.test
 * @Description
 * @date 2017/8/18
 * @Copyrigth 版权所有 (C) 2017 广州讯心信息科技有限公司.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserTest {
    private static final Logger logger = Logger.getLogger(UserTest.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    IUserAccessService userAccessService;

    @Test
    public void fun01() throws NoSuchAlgorithmException {
        User user = userAccessService.getUserByOpenId("1234");

        logger.info(JSON.toJSONString(user));
        if (user != null) {
            // 用户存在，则静默登陆，创建或更新usertoken，然后跳转到首页
            Map<String, Object> res = userAccessService.login(user, Const.DELLOGIN);
            logger.info(JSON.toJSONString(res));
        }else{
            // 没绑定openId或是没注册, 则跳转到登陆绑定界面
            logger.warn("没绑定openId或是没注册");
        }
    }
}
