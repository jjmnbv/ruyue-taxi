package com.szyciov.supervision.mq;

import com.szyciov.supervision.SupervisionApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gps数据mq消息模拟
 * Created by lzw on 2017/8/21.
 */
public class GpsServiceMqTest extends SupervisionApplicationTests {
    private @Autowired
    MessageSender messageSender;
}
