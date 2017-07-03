package com.szyciov.carservice.util.fdfs.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public abstract class Pool {
	/**
	 * GenericObjectPool
	 */
	private final GenericObjectPool internalPool;

	/**
	 *
	 * @param poolConfig
	 *            poolConfig
	 * @param factory
	 *            factory
	 */
	public Pool(GenericObjectPool.Config poolConfig,
			PoolableObjectFactory factory) {
		this.internalPool = new GenericObjectPool(factory, poolConfig);
	}

	/**
	 * getResource
	 * 
	 * @return StorageClient
	 * @throws Exception
	 *             Exception 从Pool获取一个对象,此操作将导致一个"对象"从Pool移除(脱离Pool管理),
	 *             调用者可以在获得"对象"引用后即可使用,且需要在使用结束后"归还"
	 */
	public StorageClient getResource() throws Exception {
		try {
			return (StorageClient) this.internalPool.borrowObject();
		} catch (Exception e) {
			throw new Exception("Could not get a resource from the pool", e);
		}
	}

	/**
	 * returnResource
	 * 
	 * @param resource
	 *            resource
	 * @throws Exception
	 *             Exception 归还"对象,当"对象"使用结束后,需要归还到Pool中,才能维持Pool中对象的数量可控,
	 *             如果不归还到Pool,那么将意味着在Pool之外,将有大量的"对象"存在,那么就使用了"对象池"的意义
	 */
	public void returnResource(StorageClient resource) throws Exception {
		try {
			this.internalPool.returnObject(resource);
		} catch (Exception e) {
			throw new Exception("Could not return the resource to the pool", e);
		}
	}

	/**
	 * returnBrokenResource
	 * 
	 * @param resource
	 *            resource
	 * @throws Exception
	 *             Exception 销毁对象,直接调用ObjectFactory.destroyObject(obj);
	 */
	public void returnBrokenResource(StorageClient resource) throws Exception {
		try {
			this.internalPool.invalidateObject(resource);
		} catch (Exception e) {
			throw new Exception("Could not return the resource to the pool", e);
		}
	}

	/**
	 * destroy
	 * 
	 * @throws Exception
	 *             Exception 销毁连接池
	 */
	public void destroy() throws Exception {
		try {
			this.internalPool.close();
		} catch (Exception e) {
			throw new Exception("Could not destroy the pool", e);
		}
	}
}