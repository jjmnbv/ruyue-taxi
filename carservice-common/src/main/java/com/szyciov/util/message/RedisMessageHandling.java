package com.szyciov.util.message;

import com.szyciov.message.redis.RedisMessage;

/**
 * redis消息处理
 * Created by LC on 2017/3/16.
 */
public interface RedisMessageHandling {

    /**
     *
     * 存储消息
     * @return
     */
    boolean pushMessage(RedisMessage redisMessage) throws Exception;

    /**
     * 获取消息
     * @return
     * @throws Exception
     */
    String getMessage(RedisMessage redisMessage) throws Exception;

}
