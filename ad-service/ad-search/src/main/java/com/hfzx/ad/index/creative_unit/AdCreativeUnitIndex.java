package com.hfzx.ad.index.creative_unit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hfzx.ad.index.IndexAware;
import com.hfzx.ad.index.adunit.AdUnitObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author: zhangyu
 * @Description: key: creativeId-unitId, value: creativeUnitObject
 * @Date: in 2020/4/25 13:58
 */
@Slf4j
@Component
public class AdCreativeUnitIndex implements IndexAware<String, AdCreativeUnitObject> {

    private static Map<String, AdCreativeUnitObject> objectMap;
    // <creativeId, Set<unitId>>
    private static Map<Long, Set<Long>> creativeUnitMap;
    // <unitId, Set<creativeId>>
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = Maps.newConcurrentMap();
        creativeUnitMap = Maps.newConcurrentMap();
        unitCreativeMap = Maps.newConcurrentMap();
    }

    /**
     * 根据unitObject列表获取创意ID列表
     * @param unitObjects
     * @return
     */
    public List<Long> selectCreativeIds(List<AdUnitObject> unitObjects) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return Collections.emptyList();
        }

        List<Long> creativeIds = Lists.newArrayList();
        unitObjects.forEach(unitObject -> {
            Set<Long> creativeIdSet = unitCreativeMap.get(unitObject.getUnitId());
            if (CollectionUtils.isNotEmpty(creativeIdSet)) {
                creativeIds.addAll(creativeIdSet);
            }
        });

        return creativeIds;
    }

    @Override
    public AdCreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, AdCreativeUnitObject value) {
        log.info("CreativeUnitIndex: before add -> {}", objectMap);
        objectMap.put(key, value);

        Set<Long> unitIdSet = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isEmpty(unitIdSet)) {
            unitIdSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getCreativeId(), unitIdSet);
        }
        unitIdSet.add(value.getUnitId());

        Set<Long> creativeIdSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeIdSet)) {
            creativeIdSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeIdSet);
        }
        creativeIdSet.add(value.getCreativeId());

        log.info("CreativeUnitIndex: after add -> {}", objectMap);
    }

    @Override
    public void update(String key, AdCreativeUnitObject value) {
        log.error("CreativeUnitIndex: CreativeUnit index can not support update");
    }

    @Override
    public void delete(String key, AdCreativeUnitObject value) {
        log.info("CreativeUnitIndex: before delete -> {}", objectMap);

        objectMap.remove(key, value);

        Set<Long> unitIdSet = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isNotEmpty(unitIdSet))
            unitIdSet.remove(value.getUnitId());

        Set<Long> creativeIdSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeIdSet))
            creativeIdSet.remove(value.getCreativeId());

        log.info("CreativeUnitIndex: after delete -> {}", objectMap);
    }
}
