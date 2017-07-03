package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.RedisMessageDao;
import com.szyciov.entity.PlatformType;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.util.message.RedisListMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * redis消息操作service
 * Created by LC on 2016/11/23.
 */
@Service
public class RedisMessageService {

    @Resource
    private RedisMessageDao redisMessageDao;

    private Logger logger = LoggerFactory.getLogger(RedisMessageService.class);

    /**
     * 返回订单redis消息Key
     * @param toSystem      发送系统
     * @param payMethod     订单类型 0、1 个人订单，2 机构订单
     * @param organId       机构ID
     * @param compayId      租赁公司ID
     * @param cityCode      上车城市
     * @return
     */
    private List<String> getOrderRedisMessageKeys(String toSystem,String payMethod,String organId,
                                                  String compayId,String cityCode){
        List<String> roleIds = null;
        if(PlatformType.LEASE.code.equals(toSystem)){
            roleIds = redisMessageDao.findOrgRoleId(payMethod,organId);
            //添加租赁端超管标识 租赁端超管增加 租赁公司ID标识；
            roleIds.add(RedisKeyEnum.MESSAGE_LEASE_ROLE_ADMIN.code+compayId);
        }else if (PlatformType.OPERATING.code.equals(toSystem)){
            roleIds = redisMessageDao.findOpRoleId(cityCode);
            //添加运管端超管标识
            roleIds.add(RedisKeyEnum.MESSAGE_OPERATE_ROLE_ADMIN.code);
        }
        return roleIds;
    }

    /**
     * 设置redisList消息
     * @param roleIds
     */
    private void sendOrderMessage(RedisMessage redisMessage,List<String> roleIds) throws Exception {

        if(roleIds!=null){
            for(String roleId:roleIds){
                redisMessage.setKey(roleId);
                RedisListMessage.getInstance().pushMessage(redisMessage);
            }
        }
    }



}
 