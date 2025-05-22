package com.woxqaq.im.common.route.algo.consistentHash;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.woxqaq.im.common.enums.StatusEnum.SERVER_NOT_AVAILABLE;
import com.woxqaq.im.common.exception.IMException;

public class TreeMapConsistentHash extends AbstractConsistentHash {
    private final TreeMap<Long, String> map = new TreeMap<>();

    private static final int VIRTUAL_NODE_NUM = 2;

    @Override
    protected void add(long key, String value) {
        for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
            long hash = hash("vir" + value + i);
            map.put(hash, value);
        }
        map.put(key, value);
    }

    @Override
    protected Map<String, String> remove(String value) {
        map.entrySet().removeIf(v -> v.getValue().equals(value));
        Map<String, String> res = new HashMap<>(map.entrySet().size());
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            res.put(entry.getValue(), "");
        }
        return res;
    }

    @Override
    protected void clear() {
        map.clear();
    }

    @Override
    protected String getFirstNodeValue(String value) {
        long hash = super.hash(value);
        SortedMap<Long, String> tailMap = map.tailMap(hash);
        if (!tailMap.isEmpty()) {
            return tailMap.get(tailMap.firstKey());
        }
        if (map.isEmpty()) {
            throw new IMException(SERVER_NOT_AVAILABLE);
        }
        return map.get(map.firstKey());
    }

}
