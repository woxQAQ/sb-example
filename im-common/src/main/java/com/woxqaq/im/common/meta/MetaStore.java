package com.woxqaq.im.common.meta;

import java.util.List;
import java.util.Set;

public interface MetaStore {

    void init(MetaConfig<?> config) throws Exception;

    /*
     * 获取所有可用服务
     * 
     * @return all available server
     * 
     * @throws Exception
     */
    Set<String> getAllAvailableServer() throws Exception;

    /*
     * 添加服务
     * 
     * @throws Exception
     */
    void addServer(String ip, int port, int httpPort) throws Exception;

    /*
     * 监听服务列表
     * 
     * @throws Exception
     */
    void listenServerList(ChildListener childListener) throws Exception;

    /*
     * 重建缓存
     * 
     * @throws Exception
     */
    void rebuildCache() throws Exception;

    interface ChildListener {
        void childChanged(String path, List<String> children) throws Exception;
    }
}
