package com.szyciov.util.message;

import com.szyciov.message.redis.RedisMessage;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RedisString消息处理
 * Created by LC on 2016/11/23.
 */
public class RedisStringMessage implements RedisMessageHandling {


    public static Logger logger = LoggerFactory.getLogger(RedisMessage.class);


    private RedisStringMessage redisStringMessage = null;


    private RedisStringMessage(){}


    private static class  RedisListMessageHolder{
        private static final RedisStringMessage redisListMessage = new RedisStringMessage();
    }

    public static final RedisStringMessage getInstance(){
        return RedisListMessageHolder.redisListMessage;
    }




    @Override
    public boolean pushMessage(RedisMessage redisMessage) throws Exception {
        //获取rediskey
        String stringKey = this.getStringKey(redisMessage);
        redisMessage.setNowTime(System.currentTimeMillis());
        if(redisMessage.getExTime()!=null&&redisMessage.getExTime()>0){
            int exTime = new Long(redisMessage.getExTime()/1000).intValue();
            return JedisUtil.setString(stringKey,exTime,GsonUtil.toJson(redisMessage));
        }
        return JedisUtil.setString(stringKey,GsonUtil.toJson(redisMessage));
    }

    @Override
    public String getMessage(RedisMessage redisMessage) throws Exception {
        //获取StringKey
        String stringKey = this.getStringKey(redisMessage);
        //获取对应值
        String val = JedisUtil.getString(stringKey);
        return val;
    }


    /**
     * 返回listkey
     * @param redisMessage
     * @return
     */
    private String getStringKey(RedisMessage redisMessage){
        StringBuffer key = new StringBuffer();
        key.append(redisMessage.getBusiness());
        key.append(redisMessage.getFunction());
        key.append(redisMessage.getKey());
        return key.toString();
    }
}
 