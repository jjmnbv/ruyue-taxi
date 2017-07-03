package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.OrderMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LC on 2016/11/23.
 */
@Repository
public class RedisMessageDao {

    @Autowired
    private OrderMessageMapper redisMessageMapper ;

    /**
     * 返回租赁端机构有订单角色的ID
     * @param paymethod 支付类型
     * @param dynamicid 机构ID
     * @return
     */
    public List<String> findOrgRoleId(String paymethod,String dynamicid){
        return redisMessageMapper.findOrgRoleId(dynamicid,paymethod);
    }

    /**
     * 返回运管端机构有订单角色的ID
     * @param cityId 上车城市ID
     * @return
     */
    public List<String> findOpRoleId(String cityId){
        return redisMessageMapper.findOpRoleId(cityId);
    }


}
 