package com.szyciov.carservice.util.fdfs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.UploadCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.szyciov.carservice.util.fdfs.pool.FastdfsPool;
import com.szyciov.carservice.util.fdfs.pool.StorageClient;


/**
 * 本地文件存取实现
 * @author Fisher
 *
 */
public class FastDFSFileStoreComponent implements IFastDFSComponent {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * fastdfsPool
	 */
	@Autowired
	private FastdfsPool fastdfsPool;

	/**
	 * 返回对应的文件ID,的字节流
	 *
	 * @param fileId
	 * @return
	 * @author yfcheng2@iflytek.com
	 * @created 2014年12月3日 下午8:18:13
	 * @lastModified
	 * @history
	 */
	public InputStream readFile(String fileId) throws Exception {
		return new ByteArrayInputStream(getFileById(fileId));
	}

	/**
	 * 上传文件
	 *
	 * @param file
	 *            文件 文件扩展名通过file.getName()获得
	 * @return 文件存储路径
	 * @throws Exception
	 */
	public String saveFile(File file) throws Exception {
		if (file != null) {
			return saveFile(file, FilenameUtils.getExtension(file.getName()));
		} else {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 上传文件
	 *
	 * @param file
	 *            文件
	 * @param suffix
	 *            文件扩展名，如 (jpg,txt)
	 * @return 文件存储路径
	 * @throws Exception
	 */
	public String saveFile(File file, String suffix) throws Exception {
		if (file == null || StringUtils.isBlank(suffix)) {
			return StringUtils.EMPTY;
		} else {
			return saveFile(FileUtils.readFileToByteArray(file), suffix);
		}
	}

	/**
	 * 上传文件
	 *
	 * @param fileBuff
	 *            二进制数组
	 * @param suffix
	 *            文件扩展名 ，如(jpg,txt)
	 * @return 文件存储路径
	 * @throws Exception
	 */
	public String saveFile(byte[] fileBuff, String suffix) throws Exception {
		String fastDFSFileId = StringUtils.EMPTY;
		StorageClient storageClient = null;
		try {
			storageClient = fastdfsPool.getResource();
			fastDFSFileId = storageClient.upload_file1(fileBuff, suffix, null);
		} catch (Exception e) {
			logger.error(e.getMessage() + " fastdfs upload file is wrong", e);
			throw e;
		} finally {
			if (storageClient != null) {
				fastdfsPool.returnResource(storageClient);
			}
		}
		return fastDFSFileId;
	}
	
	/**
	 * @see com.iflytek.fastdfs.IFastDFSComponent#saveFile(byte[], java.lang.String, org.csource.fastdfs.UploadCallback)
	 */
	public String saveFile(final byte[] fileBuff, String suffix, UploadCallback callback) throws Exception {
		String fastDFSFileId = StringUtils.EMPTY;
		StorageClient storageClient = null;
		try {
			storageClient = fastdfsPool.getResource();
			fastDFSFileId = storageClient.upload_file1(null, fileBuff.length, callback, suffix, null);
		} catch (Exception e) {
			logger.error(e.getMessage() + " fastdfs upload file is wrong", e);
			throw e;
		} finally {
			if (storageClient != null) {
				fastdfsPool.returnResource(storageClient);
			}
		}
		return fastDFSFileId;
	}
	
	public boolean appendFile(String fileId,long fileSize, UploadCallback callback) throws Exception {
		StorageClient storageClient;
		try {
			storageClient = fastdfsPool.getResource();
		} catch (Exception e) {
			logger.error(e.getMessage()
					+ " saveFile localFileName fastdfs pools is wrong", e);
			throw new Exception(e);
		}
		boolean flag = false;
		try {
			flag = storageClient.append_file1(fileId, fileSize, callback) == 0 ? true : false;
		} catch (Exception e) {
			logger.error(e.getMessage() + " fastdfs upload file is wrong", e);
			fastdfsPool.returnBrokenResource(storageClient);
			throw new Exception(e);
		}
		fastdfsPool.returnResource(storageClient);
		return flag;
		
	}

	/**
	 * 删除文件
	 *
	 * @param fileId
	 *            包含group及文件目录和名称信息
	 * @return true 删除成功，false删除失败
	 * @throws java.io.IOException
	 * @throws Exception
	 */
	public boolean deleteFile(String fileId) throws Exception {
		StorageClient storageClient;
		try {
			storageClient = fastdfsPool.getResource();
		} catch (Exception e) {
			logger.error(e.getMessage()
					+ " saveFile localFileName fastdfs pools is wrong", e);
			throw new Exception(e);
		}
		boolean flag = false;
		try {
			flag = storageClient.delete_file1(fileId) == 0 ? true : false;
		} catch (Exception e) {
			logger.error(e.getMessage() + " fastdfs upload file is wrong", e);
			fastdfsPool.returnBrokenResource(storageClient);
			throw new Exception(e);
		}
		fastdfsPool.returnResource(storageClient);
		return flag;

	}

	/**
	 * 查找文件内容
	 *
	 * @param fileId
	 * @return 文件内容的二进制流
	 * @throws java.io.IOException
	 * @throws Exception
	 */
	public byte[] getFileById(String fileId) throws Exception {
		StorageClient storageClient = null;
		byte[] bytes = null;
		try {
			storageClient = fastdfsPool.getResource();
			bytes = storageClient.download_file1(fileId);
		} catch (Exception e) {
			logger.error(e.getMessage() + " fastdfs upload file is wrong", e);
			throw new Exception(e);
		} finally {
			if (storageClient != null) {
				fastdfsPool.returnResource(storageClient);
			}
		}
		return bytes;
	}
	
	public String uploadAppenderFile(byte[] fileBuff, String suffix) throws Exception {
		String fastDFSFileId = StringUtils.EMPTY;
		StorageClient storageClient = null;
		try {
			storageClient = fastdfsPool.getResource();
			fastDFSFileId = storageClient.upload_appender_file1(fileBuff, suffix, null);
		} catch (Exception e) {
			logger.error(e.getMessage() + " fastdfs upload file is wrong", e);
			throw e;
		} finally {
			if (storageClient != null) {
				fastdfsPool.returnResource(storageClient);
			}
		}
		return fastDFSFileId;
	}

	@Override
	public int truncateFile(String fileId) throws Exception {
		StorageClient storageClient = null;
		try {
			storageClient = fastdfsPool.getResource();
			return storageClient.truncate_file1(fileId);
		} catch (Exception e) {
			logger.error(e.getMessage() + " fastdfs truncate file is wrong", e);
			throw e;
		} finally {
			if (storageClient != null) {
				fastdfsPool.returnResource(storageClient);
			}
		}
	}

	@Override
	public int appendFile(String appendFileId, byte[] fileBuff)
			throws Exception {
		StorageClient storageClient = null;
		try {
			storageClient = fastdfsPool.getResource();
			return storageClient.append_file1(appendFileId, fileBuff);
		} catch (Exception e) {
			logger.error(e.getMessage() + " fastdfs append file is wrong", e);
			throw e;
		} finally {
			if (storageClient != null) {
				fastdfsPool.returnResource(storageClient);
			}
		}
	}
	
}
