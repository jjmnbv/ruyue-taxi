package com.szyciov.supervision.mq;

import com.supervision.dto.SupervisionDto;
import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.SupervisionApplicationTests;
import com.szyciov.supervision.api.dto.operation.OperationDeparture;
import org.junit.Assert;
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

    /**
     * 营运出发--测试运营订单
     * @throws Exception
     */
    @Test
    public void operationDeparture() throws Exception {

        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.OPERATION);
        supervisionDto.setCommandEnum(CommandEnum.OperationDeparture);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("orderno","CGI1704191200002");
        map.put("ordertype","1");
        map.put("usetype","2");
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }
    /**
     * 营运出发--测试机构订单
     * @throws Exception
     */
    @Test
    public void operationDeparture2() throws Exception {
        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.OPERATION);
        supervisionDto.setCommandEnum(CommandEnum.OperationDeparture);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("orderno","BCI1704200900001");
        map.put("ordertype","1");
        map.put("usetype","0");
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }

    /**
     * 营运到达--测试运营订单
     * @throws Exception
     */
    @Test
    public void operationArrival() throws Exception {

        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.OPERATION);
        supervisionDto.setCommandEnum(CommandEnum.OperationArrival);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("orderno","CGI1704191200002");
        map.put("ordertype","1");
        map.put("usetype","2");
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }
    /**
     * 营运到达--测试机构订单
     * @throws Exception
     */
    @Test
    public void operationArrival2() throws Exception {
        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.OPERATION);
        supervisionDto.setCommandEnum(CommandEnum.OperationArrival);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("orderno","BCI1704200900001");
        map.put("ordertype","1");
        map.put("usetype","0");
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }

    /**
     * 营运支付--测试运营订单
     * @throws Exception
     */
    @Test
    public void operationPay() throws Exception {

        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.OPERATION);
        supervisionDto.setCommandEnum(CommandEnum.OperationPay);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("orderno","CGI1704191200002");
        map.put("ordertype","1");
        map.put("usetype","2");
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }
    /**
     * 营运到达--测试机构订单
     * @throws Exception
     */
    @Test
    public void operationPay2() throws Exception {
        SupervisionDto supervisionDto=new SupervisionDto();
        supervisionDto.setInterfaceType(InterfaceType.OPERATION);
        supervisionDto.setCommandEnum(CommandEnum.OperationPay);
        supervisionDto.setTimestamp(System.currentTimeMillis());
        Map<String,String> map=new HashMap<>();
        map.put("orderno","BCI1704200900001");
        map.put("ordertype","1");
        map.put("usetype","0");
        supervisionDto.setDataMap(map);
        messageSender.send(supervisionDto);
    }
}
