package com.szyciov.carservice.util.fdfs.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class FastdfsPool extends Pool {
	/**
	 * FastdfsPool
	 * 
	 * @param poolConfig
	 *            poolConfig
	 * @param factory
	 *            factory
	 */
	public FastdfsPool(GenericObjectPool.Config poolConfig,
			PoolableObjectFactory factory) {
		super(poolConfig, factory);
	}

	/**
	 * FastdfsPool
	 * 
	 * @param poolConfig
	 *            poolConfig
	 */
	public FastdfsPool(GenericObjectPool.Config poolConfig) {
		super(poolConfig, new FastdfsClientFactory());
	}
	
}