package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.RedisMessageService;
import com.szyciov.dto.orderMessage.LabourMessageDto;
import com.szyciov.dto.orderMessage.SuccessMessageDto;
import com.szyciov.entity.Retcode;
import com.szyciov.util.message.RedisListMessage;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * redis消息操作controller
 * Created by LC on 2016/11/23.
 */
@RestController
@RequestMapping("api/RedisMessage")
public class RedisMessageController {

    private Logger logger = LoggerFactory.getLogger(RedisMessageController.class);

    @Resource
    private RedisMessageService redisMessageService ;

    /**
     * 添加一个人工派单消息
     * @param orgOrder 机构订单对象
     *
    @RequestMapping(value="/order/sendLabourMessage", method = RequestMethod.POST)
    public JSONObject sendLabourMessage(@RequestBody LabourMessageDto orgOrder){
        JSONObject result = new JSONObject();
        try{
            redisMessageService.sendLabourMessage(orgOrder);
            result.put("status", Retcode.OK.code);
            result.put("message", Retcode.OK.msg);
        }catch (Exception e){
            logger.error("----------------添加待人工派单消息出错!!!---------------",e);
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }
        return result;
    } **/

    /**
     *
     * 获取订单消息
     * @param type
     * @param key  key值

    @RequestMapping(value="/order/getOrderMessage/{key}/{type}", method = RequestMethod.POST)
    public JSONObject getOrderMessage(@PathVariable("key") String key,@PathVariable("type") String type){
        JSONObject result = new JSONObject();
        try{
            String val = RedisListMessage.getListMessage(key,type);
            result.put("status", Retcode.OK.code);
            result.put("message", Retcode.OK.msg);
            result.put("data",val);
            logger.info("----------------获取redis消息:key:{},value:{}",key,val);
        }catch (Exception e){
            logger.error("----------------获取订单消息出错!!!---------------",e);
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }
        return result;
    } */

    /**
     * 添加一个抢单成功消息
     * @param messageDto 抢单成功消息
     *

    @RequestMapping(value="/order/sendSuccessMessage", method = RequestMethod.POST)
    public JSONObject sendSuccessMessage(@RequestBody SuccessMessageDto messageDto){
        JSONObject result = new JSONObject();
        try{
            redisMessageService.sendSuccessMessage(messageDto);
            result.put("status", Retcode.OK.code);
            result.put("message", Retcode.OK.msg);
        }catch (Exception e){
            logger.error("----------------添加抢单成功消息出错!!!---------------",e);
            result.put("status", Retcode.EXCEPTION.code);
            result.put("message", Retcode.EXCEPTION.msg);
        }
        return result;
    }
     **/

}
 