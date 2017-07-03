package com.szyciov.carservice.util.fdfs;

import java.io.File;
import java.io.InputStream;

import org.csource.fastdfs.UploadCallback;

/**
 * 分布式文件存取接口
 * @author Fisher
 *
 */
public interface IFastDFSComponent {


    /**
     * 上传文件
     *
     * @param file 文件
     *             文件扩展名通过file.getName()获得
     * @return 文件存储路径
     * @throws Exception
     */
    public String saveFile(File file) throws Exception;

    /**
     * 上传文件
     *
     * @param file   文件
     * @param suffix 文件扩展名，如 (jpg,txt)
     * @return 文件存储路径
     * @throws Exception
     */
    public String saveFile(File file, String suffix) throws Exception;

    /**
     * 上传文件
     *
     * @param fileBuff 二进制数组
     * @param suffix   文件扩展名 ，如(jpg,txt)
     * @return 文件存储路径
     * @throws Exception
     */
    public String saveFile(byte[] fileBuff, String suffix) throws Exception;
    
    /**
     * 上传文件
     *
     * @param fileBuff 二进制数组
     * @param suffix   文件扩展名 ，如(jpg,txt)
     * @param callback   回调函数
     * @return 文件存储路径
     * @throws Exception
     */
    public String saveFile(byte[] fileBuff, String suffix, UploadCallback callback) throws Exception;

    /**
     * 删除文件
     *
     * @param fileId 包含group及文件目录和名称信息
     * @return true 删除成功，false删除失败
     * @throws Exception
     */
    public boolean deleteFile(String fileId) throws Exception;

    /**
     * 查找文件内容
     *
     * @param fileId
     * @return 文件内容的二进制流
     * @throws Exception
     */
    public byte[] getFileById(String fileId) throws Exception;

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
    public InputStream readFile(String fileId) throws Exception;
    
    public boolean appendFile(String fileId,long fileSize, UploadCallback callback) throws Exception;

	public String uploadAppenderFile(byte[] fileBuff, String suffix) throws Exception;
	
	/**
	 * 清空文件内容
	 * @author ssyang
	 * @param fileId
	 * @return 0（success） or error code
	 */
	public int truncateFile(String appendFileId) throws Exception;
	
	/**
	 * 文件追加
	 * @param appendFileId
	 * @param fileBuff
	 * @return 0（success） or error code
	 * @throws Exception
	 * @author ssyang
	 */
	public int appendFile(String appendFileId,byte[] fileBuff) throws Exception;
}
