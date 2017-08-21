package com.szyciov.supervision.mq;

import com.szyciov.supervision.SupervisionApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础数据mq消息模拟
 * Created by lzw on 2017/8/21.
 */
public class BasicServiceMqTest extends SupervisionApplicationTests {
    private @Autowired
    MessageSender messageSender;
}
