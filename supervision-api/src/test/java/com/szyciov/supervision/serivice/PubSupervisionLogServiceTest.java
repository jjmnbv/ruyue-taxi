package com.szyciov.supervision.serivice;

import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.entity.PubSupervisionLog;
import com.szyciov.supervision.serivice.impl.PubSupervisionLogServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by lzw on 2017/8/18.
 */
@Transactional
public class PubSupervisionLogServiceTest extends SupervisionApplicationTests{
    @Autowired
    private PubSupervisionLogService pubSupervisionLogService;
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

        int r= pubSupervisionLogService.insert(pubSupervisionLog);
        Assert.assertEquals(r,1);
    }
}