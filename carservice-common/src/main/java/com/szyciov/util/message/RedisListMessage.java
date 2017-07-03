package com.szyciov.util.message;

import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RedisList消息处理
 * Created by LC on 2016/11/23.
 */
public class RedisListMessage implements RedisMessageHandling {


    public static Logger logger = LoggerFactory.getLogger(RedisMessage.class);


    private RedisListMessage redisListMessage = null;


    private RedisListMessage(){}


    private static class  RedisListMessageHolder{
        private static final RedisListMessage redisListMessage = new RedisListMessage();
    }

    public static final RedisListMessage getInstance(){
        return RedisListMessageHolder.redisListMessage;
    }

    @Override
    public boolean pushMessage(RedisMessage redisMessage) throws Exception {

        String listKey = this.getListKey(redisMessage);
        redisMessage.setNowTime(System.currentTimeMillis());
        logger.info("redis消息推送内容---key:{},value:{}",new String[]{listKey,GsonUtil.toJson(redisMessage)});
        return JedisUtil.lPush(listKey,GsonUtil.toJson(redisMessage));
    }

    @Override
    public String getMessage(RedisMessage redisMessage) throws Exception {
        //获取listKey
        String listKey = this.getListKey(redisMessage);
        logger.info("获取redis消息推送内容---key:{},value:{}",new String[]{listKey,GsonUtil.toJson(redisMessage)});
        return this.getMessageByKey(listKey);
    }


    /**
     * 根据key返回message消息
     * @param key
     * @return
     */
    public String getMessageByKey(String key){

        //获取对应值
        String val = JedisUtil.rpop(key);

        logger.info("获取redis推送内容---key:{},value:{}",new String[]{key,val});
        //如果值不为空
        if(StringUtils.isNotEmpty(val)) {
            //将值转换成对象
            RedisMessage message = GsonUtil.fromJson(val,RedisMessage.class);
            if (message.getExTime()> 0) {
                //如果超时 取下一条信息
                if(this.isTimeOut(message)){
                    logger.info("redis超时，重新获取---key:{},value:{},nowTime:{}",new String[]{key,val, System.currentTimeMillis()+""});
                    return this.getMessageByKey(key);
                }
            }

            return val;
        }
        return null;
    }

    /**
     * 返回listkey
     * @param redisMessage
     * @return
     */
    private String getListKey(RedisMessage redisMessage){
        StringBuffer key = new StringBuffer();
        key.append(redisMessage.getBusiness());
        key.append(RedisKeyEnum.MESSAGE_LIST.code);
        key.append(redisMessage.getFunction());
        key.append(redisMessage.getKey());
        return key.toString();
    }


    /**
     * 是否超时
     * 当前系统毫秒数-存入毫秒数>设置超时毫秒数 =超时
     * @param  redisMessage 消息对象
     * @return
     */
    private boolean isTimeOut(RedisMessage redisMessage){

        return System.currentTimeMillis()-redisMessage.getNowTime()>redisMessage.getExTime();

    }



}
 