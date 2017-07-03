package com.szyciov.carservice.util.fdfs.pool;

import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

public class StorageClient extends StorageClient1 {
    /**
     * 跟踪器服务端
     */
    public TrackerServer trackerServer;
    /**
     * 存储服务器
     */
    public StorageServer storageServer;

    /**
     * 析构函数
     */
    public StorageClient() {
    }

    /**
     * 析构函数
     * @param trackerServer trackerServer
     * @param storageServer storageServer
     */
    public StorageClient(TrackerServer trackerServer, StorageServer storageServer) {
        super(trackerServer, storageServer);
        this.trackerServer = trackerServer;
        this.storageServer = storageServer;
    }

    /**
     * Getter for property 'trackerServer'.
     *
     * @return Value for property 'trackerServer'.
     */
    public TrackerServer getTrackerServer() {
        return trackerServer;
    }

    /**
     * Setter for property 'trackerServer'.
     *
     * @param trackerServer Value to set for property 'trackerServer'.
     */
    public void setTrackerServer(TrackerServer trackerServer) {
        this.trackerServer = trackerServer;
    }

    /**
     * Getter for property 'storageServer'.
     *
     * @return Value for property 'storageServer'.
     */
    public StorageServer getStorageServer() {
        return storageServer;
    }

    /**
     * Setter for property 'storageServer'.
     *
     * @param storageServer Value to set for property 'storageServer'.
     */
    public void setStorageServer(StorageServer storageServer) {
        this.storageServer = storageServer;
    }
}