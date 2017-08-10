package com.xxkj.passenger.wechat.dao;

import com.xxkj.passenger.wechat.entity.User;
import com.xxkj.passenger.wechat.service.UserAccessService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserAccessDaoTest{

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    UserAccessService userAccessService;

    @Test
    public void fun01() {
        System.out.println("sqlSessionFactory:" + sqlSessionFactory);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("getUserByOpenId", "dd3khg23#3j2kj3h5");
        System.out.println(user);
    }

    @Test
    public void fun02() {
        boolean isBindOpenId = userAccessService.verifyOpenId("dd3khg23#3j2kj3h5");
        System.out.println("是否绑定openid：" + isBindOpenId);
    }

}
