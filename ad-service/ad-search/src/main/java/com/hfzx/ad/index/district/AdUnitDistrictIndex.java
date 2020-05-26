package com.hfzx.ad.index.district;

import com.google.common.collect.Maps;
import com.hfzx.ad.index.IndexAware;
import com.hfzx.ad.search.vo.feature.DistrictFeature;
import com.hfzx.ad.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 13:35
 */
@Slf4j
@Component
public class AdUnitDistrictIndex implements IndexAware<String, Set<Long>> {

    /**
     * key: province-city, value: Set<unitId>
     */
    private static Map<String, Set<Long>> districtUnitMap;

    /**
     * key: unitId, value: Set<District>
     */
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = Maps.newConcurrentMap();
        unitDistrictMap = Maps.newConcurrentMap();
    }

    /**
     * 根据推广单元Id获取该推广单元关联的DistrictList, 判断该推广单元下的DistrictList是否包含请求中携带的地域限制
     * @param adUnitId
     * @param districts
     * @return
     */
    public boolean match(Long adUnitId, List<DistrictFeature.ProvinceAndCity> districts) {
        if (unitDistrictMap.containsKey(adUnitId) && CollectionUtils.isNotEmpty(districts)) {
            Set<String> unitDistricts = unitDistrictMap.get(adUnitId);
            List<String> targetDistricts = districts.stream().map(district -> CommonUtils.stringConcat(district.getProvince(), district.getCity())).collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts, unitDistricts); //a是否是b的子集
        }
        return false;
    }

    @Override
    @SuppressWarnings("all")
    public Set<Long> get(String key) {
        if (key == null)
            return Collections.emptySet();
        Set<Long> result = districtUnitMap.get(key);
        if (result == null)
            return Collections.emptySet();
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndex: before add -> {}", unitDistrictMap);
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        for (Long unitId : value) {
            Set<String> districtSet = CommonUtils.getOrCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districtSet.add(key);
        }
        log.info("UnitDistrictIndex: after add -> {}", unitDistrictMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitDistrictIndex: district index can not support update");
    }

    @Override
    @SuppressWarnings("all")
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndex: before delete -> {}", unitDistrictMap);
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.removeAll(value);

        for (Long unitId : value) {
            Set<String> districtSet = CommonUtils.getOrCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districtSet.remove(key);
        }
        log.info("UnitDistrictIndex: after delete -> {}", unitDistrictMap);
    }

}

