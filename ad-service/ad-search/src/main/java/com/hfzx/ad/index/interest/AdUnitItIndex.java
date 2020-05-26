package com.hfzx.ad.index.interest;

import com.google.common.collect.Maps;
import com.hfzx.ad.index.IndexAware;
import com.hfzx.ad.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author: zhangyu
 * @Description: key: itTag, value: Set<unitId>
 * @Date: in 2020/4/25 13:12
 */
@Slf4j
@Component
public class AdUnitItIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> itUnitMap;
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = Maps.newConcurrentMap();
        unitItMap = Maps.newConcurrentMap();
    }

    public boolean match(Long unitId, List<String> itTags) {
        if (unitItMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {
            Set<String> itSet = unitItMap.get(unitId);
            return CollectionUtils.isSubCollection(itSet, itTags);
        }
        return false;
    }

    @Override
    @SuppressWarnings("all")
    public Set<Long> get(String key) {
        if (key == null)
            return Collections.emptySet();
        Set<Long> result = itUnitMap.get(key);
        if (result == null)
            return Collections.emptySet();
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex: before add -> {}", unitItMap);
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> itSet = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            itSet.add(key);
        }

        log.info("UnitItIndex: after add -> {}", unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitItIndex: it index can not support update");
    }

    @Override
    @SuppressWarnings("all")
    public void delete(String key, Set<Long> value) {
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.removeAll(value);

        for (Long unitId : value) {
            Set<String> itSet = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            itSet.remove(key);
        }
    }



}
