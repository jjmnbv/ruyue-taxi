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
 * 运营数据mq消息模拟
 * Created by lzw on 2017/8/21.
 */
public class OperationServiceMqTest extends SupervisionApplicationTests {
    private @Autowired
    MessageSender messageSender;

    /**
     * 营运上线
     */
    @Test
    public void vehicleOperationOnline(){
        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.OPERATION);
        supervisionDto.setCommandEnum(CommandEnum.VehicleOperationOnline);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("driverId","098A6A32-FE04-4BC3-872D-11183B519ADA");
        map.put("loginTime",getNowTime());
        map.put("longitude","12");
        map.put("latitude","20");
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }

    /**
     * 车辆营运下线
     */
    @Test
    public void vehicleOperationOffline(){
        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.OPERATION);
        supervisionDto.setCommandEnum(CommandEnum.VehicleOperationOffline);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("driverId","098A6A32-FE04-4BC3-872D-11183B519ADA");
        map.put("logoutTime",getNowTime());
        map.put("longitude","12");
        map.put("latitude","20");
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }
}
