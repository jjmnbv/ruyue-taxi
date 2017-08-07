package com.xxkj.passenger.wechat.dao;

import com.xxkj.passenger.wechat.PassengerWechatApiApplicationTests;
import com.xxkj.passenger.wechat.dao.PubSupervisionLogDao;
import com.xxkj.passenger.wechat.entity.PubSupervisionLog;

import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by 林志伟 on 2017/7/26.
 */
public class PubSupervisionLogDaoTest extends PassengerWechatApiApplicationTests {
    @Autowired
    private PubSupervisionLogDao pubSupervisionLogDao;
    @Test
    public void insert() throws Exception {
        PubSupervisionLog pubSupervisionLog=new PubSupervisionLog();
        pubSupervisionLog.setDirect(1);
        pubSupervisionLog.setInterfaceCommand("xxxx");
        pubSupervisionLog.setInterfaceType("dffdf");
        pubSupervisionLog.setRequestHeader("dfkjkjfksjfk");
        pubSupervisionLog.setRequestParam("djahhashashfj");
        pubSupervisionLog.setResponceCode(630);
        pubSupervisionLog.setResponceContent("响应内容");
        pubSupervisionLog.setCreatetime(new Date());
        pubSupervisionLog.setStatus(1);

        int r=pubSupervisionLogDao.insert(pubSupervisionLog);
        Assert.assertEquals(r,1);
    }

}