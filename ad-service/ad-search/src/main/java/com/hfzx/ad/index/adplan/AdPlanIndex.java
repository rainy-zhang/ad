package com.hfzx.ad.index.adplan;

import com.google.common.collect.Maps;
import com.hfzx.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: zhangyu
 * @Description: 推广计划索引实现类
 * @Date: in 2020/4/25 10:34
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    private static Map<Long, AdPlanObject> objectMap;

    static {
        objectMap = Maps.newConcurrentMap();
    }

    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("AdPlanIndex: before add -> {}", objectMap);
        objectMap.put(key, value);
        log.info("AdPlanIndex: after add -> {}", objectMap);
    }

    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("AdPlanIndex: before update -> {}", objectMap);
        AdPlanObject oldPlanObject = objectMap.get(key);
        if (null == oldPlanObject) {
            objectMap.put(key, value);
        } else {
            oldPlanObject.update(value);
        }
        log.info("AdPlanIndex: after update -> {}", objectMap);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("AdPlanIndex: before delete -> {}", objectMap);
        objectMap.remove(key, value);
        log.info("AdPlanIndex: after delete -> {}", objectMap);
    }
}
