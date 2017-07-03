package com.szyciov.carservice.util.fdfs.pool;

import java.io.IOException;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.Resource;

public class FastdfsClientFactory extends BasePoolableObjectFactory {
	
	private Resource configFilePath;
	
	public void setConfigFilePath(Resource configFilePath) {
		this.configFilePath = configFilePath;
	}

	/**
	 * makeObject
	 * 
	 * @return Object
	 * @throws Exception
	 *             Exception 创建一个新对象;当对象池中的对象个数不足时,
	 *             将会使用此方法来"输出"一个新的"对象",并交付给对象池管理.
	 */
	public Object makeObject() throws Exception {
		/*
		 * String classPath = new File(super.getClass().getResource("/")
		 * .getFile()).getCanonicalPath();
		 */
		// String configFilePath =
		// GlobalConfig.getInstance().getString("fastdfs_config_address");
		ClientGlobal.init(configFilePath.getFile().getAbsolutePath());
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient client = new StorageClient(trackerServer, storageServer);
		return client;
	}

	/**
	 *
	 * @param obj
	 * @throws Exception
	 *             销毁对象,如果对象池中检测到某个"对象"idle的时间超时,
	 *             或者操作者向对象池"归还对象"时检测到"对象"已经无效,那么此时将会导致"对象销毁";
	 *             "销毁对象"的操作设计相差甚远,但是必须明确:
	 *             当调用此方法时,"对象"的生命周期必须结束.如果object是线程,那么此时线程必须退出;
	 *             如果object是socket操作,那么此时socket必须关闭;
	 *             如果object是文件流操作,那么此时"数据flush"且正常关闭.
	 */
	public void destroyObject(Object obj) throws Exception {
		if ((obj == null) || (!(obj instanceof StorageClient)))
			return;
		StorageClient storageClient = (StorageClient) obj;
		TrackerServer trackerServer = storageClient.getTrackerServer();
		StorageServer storageServer = storageClient.getStorageServer();
		if (trackerServer != null) {
			trackerServer.close();
		}
		if (storageServer != null) {
			storageServer.close();
		}
	}

	/**
	 * 检测对象是否"有效";
	 * 
	 * @param obj
	 * @return Pool中不能保存无效的"对象",因此"后台检测线程"会周期性的检测Pool中"对象"的有效性,
	 *         如果对象无效则会导致此对象从Pool中移除,并destroy;此外在调用者从Pool获取一个"对象"时,
	 *         也会检测"对象"的有效性,确保不能讲"无效"的对象输出给调用者;
	 *         当调用者使用完毕将"对象归还"到Pool时,仍然会检测对象的有效性.
	 *         所谓有效性,就是此"对象"的状态是否符合预期,是否可以对调用者直接使用;
	 *         如果对象是Socket,那么它的有效性就是socket的通道是否畅通/阻塞是否超时等
	 */
	public boolean validateObject(Object obj) {
		StorageClient storageClient = (StorageClient) obj;
		try {
			if (storageClient.trackerServer != null) {
				return ProtoCommon.activeTest(storageClient.trackerServer
						.getSocket());
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}