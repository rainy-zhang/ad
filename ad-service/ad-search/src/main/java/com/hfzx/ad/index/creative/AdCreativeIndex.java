package com.hfzx.ad.index.creative;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hfzx.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyu
 * @Description: 创意索引
 * @Date: in 2020/4/25 13:48
 */
@Slf4j
@Component
public class AdCreativeIndex implements IndexAware<Long, AdCreativeObject> {

    private static Map<Long, AdCreativeObject> objectMap;

    static {
        objectMap = Maps.newConcurrentMap();
    }

    /**
     * 根据创意Id列表批量获取创意对象
     * @param creativeIds
     * @return
     */
    public List<AdCreativeObject> fetch(Collection<Long> creativeIds) {
        if (CollectionUtils.isNotEmpty(creativeIds)) {
            return Collections.emptyList();
        }
        List<AdCreativeObject> creativeObjects = Lists.newArrayList();
        creativeIds.forEach(creativeId -> {
            AdCreativeObject creativeObject = get(creativeId);
            if (null == creativeObject) {
                log.error("CreatoveIndex: creatoveObject not found: {}", creativeId);
                return;
            }
            creativeObjects.add(creativeObject);
        });
        return creativeObjects;
    }

    @Override
    public AdCreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdCreativeObject value) {
        log.info("AdCreativeIndex: before add -> {}", objectMap);
        objectMap.put(key, value);
        log.info("AdCreativeIndex: after add -> {}", objectMap);
    }

    @Override
    public void update(Long key, AdCreativeObject value) {
        log.info("AdCreativeIndex: before update -> {}", objectMap);
        AdCreativeObject oldCreativeObject = objectMap.get(key);
        if (oldCreativeObject == null)
            objectMap.put(key, value);
        else
            oldCreativeObject.update(value);
        log.info("AdCreativeIndex: after update -> {}", objectMap);
    }

    @Override
    public void delete(Long key, AdCreativeObject value) {
        log.info("AdCreativeIndex: before delete -> {}", objectMap);
        objectMap.remove(key, value);
        log.info("AdCreativeIndex: after delete -> {}", objectMap);
    }
}
