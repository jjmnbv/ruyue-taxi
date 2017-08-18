package com.szyciov.coupon.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

/**
 *  redis操作类
 * @author LC
 * @date 2017/6/8
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean setString(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 删除key
     * @param key
     * @return
     */
    public boolean delKey(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public String getString(final String key) {
        String result = null;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }


    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public String hmGet(String key, Object hashKey){
        HashOperations<String, String, String> hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     */
    public void hmDel(String key, String... hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.delete(key,hashKey);
    }


    /**
     * 哈希获取数据
     * @param key
     * @return
     */
    public Map<Object,Object> hGetAll(String key){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,String v){
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<String> lRange(String k, long l, long l1){
        ListOperations<String, String> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,String value){
        SetOperations<String, String> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<String> setMembers(String key){
        SetOperations<String, String> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,String value,double scoure){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<String> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }


    /**
     * 写入缓存设置时效时间
     * @param key           key
     * @param expireTime    超时时间
     * @param timeUnit      超时时间单位
     * @return
     */
    public boolean expire(final String key,Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 执行lua脚本
     * @return
     */
    public boolean eval(ResourceScriptSource scriptSource, List<String> keys, Object... val) {
        boolean result = false;
        try {

            DefaultRedisScript script = new DefaultRedisScript();
            script.setScriptSource(scriptSource);
            redisTemplate.execute(script,new StringRedisSerializer(),new StringRedisSerializer(), keys, val);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 保存坐标
     * 写入缓存设置时效时间
     * @param key           key
     * @param longitude     经度
     * @param latitude      纬度
     * @param member        别称
     * @param expireTime    超时时间
     * @param timeUnit      超时时间单位
     * @return
     */
    public boolean geoAdd(final String key, float longitude, float latitude,String member, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            Point point = new Point(longitude,latitude);
            GeoOperations<String, String> operations = redisTemplate.opsForGeo();
            operations.geoAdd(key,point ,member);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取坐标
     * @param key           key
     * @param member        经纬度别名
     * @return
     */
    public List<Point> geoPos(final String key, String member) {
        try {
            GeoOperations<String, String> operations = redisTemplate.opsForGeo();
            return operations.geoPos(key,member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
 