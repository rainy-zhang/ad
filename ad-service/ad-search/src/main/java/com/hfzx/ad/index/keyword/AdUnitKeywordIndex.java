package com.hfzx.ad.index.keyword;

import com.google.common.collect.Maps;
import com.hfzx.ad.index.IndexAware;
import com.hfzx.ad.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author: zhangyu
 * @Description: key: keyword, value: Set<unitId>
 * @Date: in 2020/4/25 11:03
 */
@Slf4j
@Component
public class AdUnitKeywordIndex implements IndexAware<String, Set<Long>> {

    // 反向索引： 通过关键字获取关联的推广单元
    private static Map<String, Set<Long>> keywordUnitMap;
    // 正向索引： 通过推广单元获取关键字限制维度
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = Maps.newConcurrentMap();
        unitKeywordMap = Maps.newConcurrentMap();
    }

    public boolean match(Long unitId, List<String> keywords) {
        if (unitKeywordMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {
            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            return CollectionUtils.isSubCollection(keywords, unitKeywords);  //判断集合A是否是集合B的子集
        }
        return false;
    }

    @Override
    public Set<Long> get(String key) {
        if (StringUtils.isBlank(key))
            return Collections.emptySet();
        Set<Long> result = keywordUnitMap.get(key);
        if (null == result)
            return Collections.emptySet();
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public void add(String key, Set<Long> value) {
        log.info("UnitKeywordIndex: before add -> {}", unitKeywordMap);
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }
        log.info("UnitKeywordIndex: after add -> {}", unitKeywordMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitKeywordIndex: keyword index can not support update");
    }

    @Override
    @SuppressWarnings("all")
    public void delete(String key, Set<Long> value) {
        log.info("UnitKeywordIndex: before delete -> {}", unitKeywordMap);
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.removeAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        }
        log.info("UnitKeywordIndex: after delete -> {}", unitKeywordMap);
    }


}

