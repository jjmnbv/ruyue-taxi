package com.szyciov.supervision.mq;

import com.szyciov.supervision.SupervisionApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 关系数据mq消息模拟
 * Created by lzw on 2017/8/21.
 */
public class RelationshipServiceMqTest extends SupervisionApplicationTests {
    private @Autowired
    MessageSender messageSender;
}
