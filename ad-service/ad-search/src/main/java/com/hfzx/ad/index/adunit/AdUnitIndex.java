package com.hfzx.ad.index.adunit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hfzx.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: zhangyu
 * @Description: 推广单元索引实现类
 * @Date: in 2020/4/25 10:52
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

    private static Map<Long, AdUnitObject> objectMap;

    static {
        objectMap = Maps.newConcurrentMap();
    }

    /**
     * 根据流量类型匹配推广单元索引
     * @param positionType
     * @return 推广单元Set<Id>
     */
    public Set<Long> match(Integer positionType) {
        Set<Long> unitIds = Sets.newHashSet();
        objectMap.forEach((key, value) -> {
            if (AdUnitObject.isAdSlotTypeOK(positionType, value.getPositionType())) {
                unitIds.add(key);
            }
        });
        return unitIds;
    }

    /**
     * 根据unitId集合获取推广单元
     * @param unitIds
     * @return
     */
    public List<AdUnitObject> fetch(Collection<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return Collections.emptyList();
        }
        List<AdUnitObject> unitObjects = Lists.newArrayList();
        unitIds.forEach(unitId -> {
            AdUnitObject unitObject = get(unitId);
            if (null == unitObject) {
                log.error("fetch: AdUnitObject not found: {}", unitId);
                return;
            }
            unitObjects.add(unitObject);
        });
        return unitObjects;
    }

    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("AdUnitIndex: before add -> {}", objectMap);
        objectMap.put(key, value);
        log.info("AdUnitIndex: after add -> {}", objectMap);
    }

    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("AdUnitIndex: before update -> {}", objectMap);
        AdUnitObject oldUnitObject = objectMap.get(key);
        if (oldUnitObject == null)
            objectMap.put(key, value);
        else
            oldUnitObject.update(value);
        log.info("AdUnitIndex: after update -> {}", objectMap);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        log.info("AdUnitIndex: before delete -> {}", objectMap);
        objectMap.remove(key, value);
        log.info("AdUnitIndex: after delete -> {}", objectMap);
    }
}
