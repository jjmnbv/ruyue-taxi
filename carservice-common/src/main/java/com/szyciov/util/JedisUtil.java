package com.szyciov.util;

import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * jedis操作公共类
 * Created by LC on 2016/11/21.
 */
public class JedisUtil {


    private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
	/**
	 * 锁定一个资源默认等待时间
	 */
	private static int waitTime = 50000;
	
	/**
	 * 该资源允许锁定的最长时间，超过这个时间自动解锁
	 */
	private static int timeOut = 20000;

	private static ReentrantLock sync = new ReentrantLock();

	private static Condition condition = sync.newCondition();
    
    public static String getString(String key){
        Jedis jedis = RedisUtil.getJedis();
        String val = "";
        try{
            val = jedis.get(key);
        }catch (Exception e){

        }finally {
            jedis.close();
//            RedisUtil.freedResource(jedis);
        }
        return val;
    }

    public static boolean setString(String k,String v){
        Jedis jedis = RedisUtil.getJedis();
        try{
            jedis.set(k,v);
        }catch (Exception e){
            logger.error("setString error : k:{},v:{}",new String[]{k,v.toString()},e);
            return false;
        }finally {
            jedis.close();
//            RedisUtil.freedResource(jedis);
        }
        return true;
    }

    /**
     * 返回所有key相关的
     * @param key
     * @return
     */
    public static Set<String> getKeys(String key) {
        Jedis jedis = RedisUtil.getJedis();
        try {
            return jedis.keys(key);
        } catch (Exception e) {
            logger.error("getKeys error : k:{}", key, e);
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * List数据存储
     * @param k
     * @param v
     */
    public static boolean lPush(String k,String... v){
        Jedis jedis = RedisUtil.getJedis();
        try{
            jedis.lpush(k,v);
        }catch (Exception e){
            logger.error("lPush error : k:{},v:{}",new String[]{k,v.toString()},e);
            return false;
        }finally {
//            RedisUtil.freedResource(jedis);
            jedis.close();
        }
        return true;
    }


    /**
     * 执行lua脚本
     * @param script    脚本
     * @param keyCount  key总数
     * @param params    key值
     */
    public static boolean eval(String script, int keyCount, String... params){
        Jedis jedis = RedisUtil.getJedis();
        try{
            jedis.eval(script,keyCount,params);
        }catch (Exception e){
            logger.error("eval error : k:{},count:{}v:{}",new String[]{script,keyCount+"",params.toString()},e);
            return false;
        }finally {
//            RedisUtil.freedResource(jedis);
            jedis.close();
        }
        return true;
    }
    /**
     * 设置 过期时间
     * @param key
     * @param seconds 以秒为单位
     * @param value
     */
    public static boolean setString(String key ,int seconds,String value){
        Jedis jedis = RedisUtil.getJedis();

        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("setString error:{key:{},seconds{},val:{}}"+new String[]{key,seconds+"",value});
            return false;
        } finally{
            jedis.close();
//            RedisUtil.freedResource(jedis);
        }
        return true;
    }


    /**
     * 设置 String值 如果key不存在，才设置
     * @param key     redisKey
     * @param seconds 超时时间(以秒为单位)
     * @param value   对应值
     */
    public static void setString(String key ,String value,int seconds)throws Exception{
        Jedis jedis = RedisUtil.getJedis();
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            jedis.set(key, value,"NX","EX", seconds);
        } catch (Exception e) {
            logger.error("setString error : "+e);
            throw e;
        } finally{
            jedis.close();
//            RedisUtil.freedResource(jedis);
        }
    }

    /**
     * List获取值
     * @param key
     * @return
     */
    public static String rpop(String key){
        Jedis jedis = RedisUtil.getJedis();
        String val = null;
        try{
            val = jedis.rpop(key);
        }catch (Exception e){
            logger.error("redis rpop 失败---key:{}",key,e);
        }finally {
            jedis.close();
//            RedisUtil.freedResource(jedis);
        }
        return val;
    }

    /**
     * 消息发布
     * @param chanel
     * @param value
     */
    public static void pubLish(String chanel,String value){
        Jedis jedis = RedisUtil.getJedis();
        try{
            jedis.publish(chanel,value);
        }catch (Exception e){
            logger.error("redis publish 失败---key:{}",chanel,e);
        }finally {
            jedis.close();
        }
    }
    
    /**
     * 返回流水号+1后的值
     * @param key
     * @return
     */
    public static long getFlowNO(String key){
        Jedis jedis = RedisUtil.getJedis();
        try{
           return  jedis.incr(key);
        }catch (Exception e){
            logger.error("redis expire 失败---key:{}",key,e);
        }finally {
            jedis.close();
        }
        return 0;
    }
    
    /**
     * 设置一个会超时的key
     * @param key
     * @param second
     */
    public static void expire(String key,int second){
        Jedis jedis = RedisUtil.getJedis();
        try{
            jedis.expire(key,second);
        }catch (Exception e){
            logger.error("redis expire 失败---key:{}",key,e);
        }finally {
            jedis.close();
        }
    }
    
    /**
     * 消息发布
     * @param chanel
     * @param value
     */
    public static void delKey(String key){
        Jedis jedis = RedisUtil.getJedis();
    	try{
    		jedis.del(key);
    	}catch (Exception e){
    		logger.error("redis del 失败---key:{}",key,e);
    	}finally {
    		jedis.close();
//            RedisUtil.freedResource(jedis);
    	}
    }

    /**
     * 消息订阅
     * @param pubSub
     * @param chanel
     */
    public static void pubsub(JedisPubSub pubSub,String chanel){
        Jedis jedis = RedisUtil.getJedis();
        try{
            jedis.subscribe(pubSub,chanel);
        }catch (Exception e){
            logger.error("redis pubsub 失败---chanel:{}",chanel,e);
        }finally {
            jedis.close();
//            RedisUtil.freedResource(jedis);
        }
    }
    
	public static boolean lock(String key) {
		sync.lock();
		try {
			if (getString(key) == null) {
				setString(key,"lock",timeOut);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
		} finally {
			sync.unlock();
		}
		return false;
	}
	
	public static void unLock(String key) {
		sync.lock();
		try {
			condition.signalAll();
		} finally {
			sync.unlock();
		}
		delKey(key);
	}

    public static void main(String[] args) {

        for(int i = 1000; i < 5000; i++) {
            JedisUtil.setString(i+"_test",2000,i+"");
        }
        System.out.println("成功！");

    }

}
