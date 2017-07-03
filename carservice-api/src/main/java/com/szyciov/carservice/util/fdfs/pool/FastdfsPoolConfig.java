package com.szyciov.carservice.util.fdfs.pool;

import org.apache.commons.pool.impl.GenericObjectPool;


public class FastdfsPoolConfig extends GenericObjectPool.Config {
	/**
	 * FastdfsPoolConfig
	 */
	public FastdfsPoolConfig() {
		/**
		 * 链接池中最大连接数,默认为8.
		 */
		setMaxActive(10);
		/**
		 * 链接池中最大空闲的连接数,默认为8.
		 */
		setMaxIdle(8);
		/**
		 * 连接池中最少空闲的连接数,默认为0.
		 */
		setMinIdle(1);
		/**
		 * 当连接池资源耗尽时，调用者最大阻塞的时间，超时将跑出异常。 单位，毫秒数;默认为-1.表示永不超时.
		 */
		setMaxWait(60000L);
		/**
		 * 连接空闲的最小时间，达到此值后空闲链接将会被移除， 且保留“minIdle”个空闲连接数。默认为-1.毫秒数
		 */
		setMinEvictableIdleTimeMillis(60000L);
		/**
		 * 对于“空闲链接”检测线程而言，每次检测的链接资源的个数。默认为3.
		 */
		setNumTestsPerEvictionRun(3);
		/**
		 * 向调用者输出“链接”资源时，是否检测是有有效， 如果无效则从连接池中移除，并尝试获取继续获取。默认为false。建议保持默认值.
		 */
		setTestOnBorrow(false);

		/**
		 * 向连接池“归还”链接时，是否检测“链接”对象的有效性。 默认为false。建议保持默认值.
		 */
		setTestOnReturn(true);

		/**
		 * 向调用者输出“链接”对象时，是否检测它的空闲超时 默认为false。如果“链接”空闲超时，将会被移除。建议保持默认值
		 */
		setTestWhileIdle(false);

		/**
		 * 空闲链接”检测线程，检测的周期，毫秒数。 如果为负值，表示不运行“检测线程”。默认为-1.
		 */
		setTimeBetweenEvictionRunsMillis(30000L);

		/**
		 * 当“连接池”中active数量达到阀值时，即“链接”资源耗尽时，连接池需要采取的手段, 默认为1： -> 0 : 抛出异常， -> 1 :
		 * 阻塞，直到有可用链接资源 -> 2 : 强制创建新的链接资源
		 */
		setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
	}

	public int getMaxIdle() {
		return this.maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return this.minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return this.maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public long getMaxWait() {
		return this.maxWait;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public byte getWhenExhaustedAction() {
		return this.whenExhaustedAction;
	}

	public void setWhenExhaustedAction(byte whenExhaustedAction) {
		this.whenExhaustedAction = whenExhaustedAction;
	}

	public boolean isTestOnBorrow() {
		return this.testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return this.testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestWhileIdle() {
		return this.testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return this.timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(
			long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getNumTestsPerEvictionRun() {
		return this.numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public long getMinEvictableIdleTimeMillis() {
		return this.minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public long getSoftMinEvictableIdleTimeMillis() {
		return this.softMinEvictableIdleTimeMillis;
	}

	public void setSoftMinEvictableIdleTimeMillis(
			long softMinEvictableIdleTimeMillis) {
		this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
	}
}