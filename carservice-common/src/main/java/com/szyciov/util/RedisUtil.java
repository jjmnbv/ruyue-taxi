package com.szyciov.util;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	
	private static ReentrantLock lockPool = new ReentrantLock();
	private static ReentrantLock lockJedis = new ReentrantLock();

	private static String url = "D:\\softwork\\carService\\carservice-common\\src\\main\\resources\\redis.properties";
	
	//Redis服务器IP
	private static String ADDR_ARRAY = SystemConfig.getSystemProperty("server");
	
	//Redis的端口号
	private static int PORT = Integer.parseInt(SystemConfig.getSystemProperty("port"));
	
	//Redis的密码
	private static String PASSWORD = SystemConfig.getSystemProperty("pwd");
	
	//可用连接实例的最大数目，默认值为8
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
	private static int MAX_ACTIVE = Integer.parseInt(SystemConfig.getSystemProperty("max_active"));
	
	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值为8
	private static int MAX_IDLE = Integer.parseInt(SystemConfig.getSystemProperty("max_idle"));
	
	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
	private static int MAX_WAIT = Integer.parseInt(SystemConfig.getSystemProperty("max_wait"));
	
	//超时时间
	private static int TIMEOUT = Integer.parseInt(SystemConfig.getSystemProperty("timeout"));
	
	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
	private static boolean TEST_ON_BORROW = Boolean.getBoolean(SystemConfig.getSystemProperty("test_on_borrow"));
	
	
	private static JedisPool jedisPool = null;
	
	public final static int EXRP_HOUR = 60*60;
	public final static int EXRP_DAY = 60*60*24;
	public final static int EXRP_MONTH = 60*60*24*30;
	
	/**
	 * Jedis连接池初始化
	 */
	private static void initialPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			
			if(StringUtils.isEmpty(PASSWORD)) {
				jedisPool = new JedisPool(config, ADDR_ARRAY, PORT, TIMEOUT);
			} else {
				jedisPool = new JedisPool(config, ADDR_ARRAY, PORT, TIMEOUT, PASSWORD);
			}
		} catch (Exception e) {
			logger.error("First create JedisPool error : "+e);
		}
		
	}
	
	
	public static Jedis getJedis() {
		assert ! lockJedis.isHeldByCurrentThread();
		lockJedis.lock();
		if(jedisPool == null) {
			poolInit();
		}
		
		Jedis jedis = null;
		try {
			if(jedisPool != null) {
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			logger.error("Get jedis error", e);
		} finally {
			lockJedis.unlock();
		}
		
		return jedis;
	}
	
	/**
	 * 多线程环境同步初始化
	 */
	private static void poolInit() {
		assert ! lockPool.isHeldByCurrentThread();
		lockPool.lock();
		
		try {
			if(jedisPool == null) {
				initialPool();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lockPool.unlock();
		}
	}
	
	/**
	 * 释放Jedis资源
	 * @param jedis
	 */
	public static void freedResource(final Jedis jedis) {
		if (jedis != null && jedisPool !=null) {
			jedis.close();
		}


	}
	
	
	public synchronized static void setString(String key ,String value){
		try {
			value = value == null ? "" : value;
			getJedis().set(key, value);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public synchronized static String getString(String key){
		if(getJedis() == null || !getJedis().exists(key)){
			return null;
		}
		
		return getJedis().get(key);
	}
	
	


}
