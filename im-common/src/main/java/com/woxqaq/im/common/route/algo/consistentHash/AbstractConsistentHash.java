package com.woxqaq.im.common.route.algo.consistentHash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public abstract class AbstractConsistentHash {
    /*
     * 添加虚拟节点
     * 
     * @param key
     * 
     * @param value
     */
    protected abstract void add(long key, String value);

    /*
     * 删除虚拟节点
     * 
     * @param value
     * 
     * @return current nodes
     */
    protected abstract Map<String, String> remove(String value);

    protected abstract void clear();

    /*
     * 排序节点
     */
    protected void sort() {
    };

    /*
     * 根据 key 通过一致性哈希计算虚拟节点
     * 
     * @param value
     * 
     * @return
     */
    protected abstract String getFirstNodeValue(String value);

    /*
     * 传入集群和 key，返回一个节点
     */
    public String process(List<String> cluster, String key) {
        clear();
        for (String value : cluster) {
            add(hash(value), value);
        }
        sort();

        return getFirstNodeValue(key);
    };

    public Long hash(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] buf = null;
        try {
            buf = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported", e);
        }

        md5.update(buf);
        byte[] digest = md5.digest();
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);
        long truncatedHashCode = hashCode & 0x7FFFFFFFFFFFFFFFL;
        return truncatedHashCode;
    }
}
