package com.szyciov.supervision.mq;

import com.supervision.dto.SupervisionDto;
import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.SupervisionApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 司机相关接口对象模拟发送
 * Created by lzw on 2017/8/18.
 */
public class PubDriverTest extends SupervisionApplicationTests {
    private @Autowired
    MessageSender messageSender;

    @Test
    public void driverOnWork(){
        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.ORDER);
        supervisionDto.setCommandEnum(CommandEnum.DriverOnWork);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("driverId","098A6A32-FE04-4BC3-872D-11183B519ADA");
        map.put("onWorkTime",getNowTime());
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }

    @Test
    public void driverOffWork(){
        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.ORDER);
        supervisionDto.setCommandEnum(CommandEnum.DriverOffWork);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("driverId","098A6A32-FE04-4BC3-872D-11183B519ADA");
        map.put("onWorkTime",getNowTime());
        map.put("offWorkTime",getNowTime());
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }
}
