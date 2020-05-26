package com.hfzx.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.hfzx.ad.index.CommonStatus;
import com.hfzx.ad.index.DataTable;
import com.hfzx.ad.index.adunit.AdUnitIndex;
import com.hfzx.ad.index.adunit.AdUnitObject;
import com.hfzx.ad.index.creative.AdCreativeIndex;
import com.hfzx.ad.index.creative.AdCreativeObject;
import com.hfzx.ad.index.creative_unit.AdCreativeUnitIndex;
import com.hfzx.ad.index.district.AdUnitDistrictIndex;
import com.hfzx.ad.index.interest.AdUnitItIndex;
import com.hfzx.ad.index.keyword.AdUnitKeywordIndex;
import com.hfzx.ad.search.ISearch;
import com.hfzx.ad.search.vo.SearchRequest;
import com.hfzx.ad.search.vo.SearchResponse;
import com.hfzx.ad.search.vo.feature.DistrictFeature;
import com.hfzx.ad.search.vo.feature.FeatureRelation;
import com.hfzx.ad.search.vo.feature.ItFeature;
import com.hfzx.ad.search.vo.feature.KeywordFeature;
import com.hfzx.ad.search.vo.media.AdSlot;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/11 20:26
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    /**
     * 通过@EnableCircuitBreaker注解实现, 该注解会通过AOP拦截所有@HystrixCommand注解标识的方法, 当方法执行失败时
     * 会通过反射调用回退方法,然后实现断路,
     * 就是@EnableCircuitBreaker会将使用了@HystrixCommand注解的方法扔到Hystrix自己的线程池里面,然后通过try-catch-finally把它包装起来,
     * 如果发生失败 就在catch里面或者是finally中去通过反射调用fallbackMethod中定义的方法,同时Hystrix会在内存中记录一些线程池的信息
     * 可以将这些信息展示出来: 成功调用的次数,调用失败的次数...
     * HystrixCommand的执行效率非常低
     */
    public SearchResponse fallback(SearchRequest searchRequest, Throwable e) {
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback")  //实现断路,如果方法发生异常可以指定fallbackMethod中定义的错误回退的方法, 这个方法必须要定义在当前这个类中
    public SearchResponse fetchCreatives(SearchRequest searchRequest) {
        // 请求广告位的信息
        List<AdSlot> adSlots = searchRequest.getRequestInfo().getAdSlots();

        // 3个feature
        KeywordFeature keywordFeature = searchRequest.getFeatureInfo().getKeywordFeature();
        ItFeature itFeature = searchRequest.getFeatureInfo().getItFeature();
        DistrictFeature districtFeature = searchRequest.getFeatureInfo().getDistrictFeature();
        FeatureRelation featureRelation = searchRequest.getFeatureInfo().getFeatureRelation();

        // 构造响应对象
        SearchResponse searchResponse = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Creative = searchResponse.getAdSlot2Creative();
        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIds; //过滤后的推广单元Id集合
            Set<Long> unitIds = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());

            if (featureRelation == FeatureRelation.AND) {
                filterKeywordFeature(unitIds, keywordFeature);
                filterDistrictFeature(unitIds, districtFeature);
                filterItFeature(unitIds, itFeature);
                targetUnitIds = unitIds;
            } else {
                targetUnitIds = getORRelationUnitIds(unitIds, keywordFeature, districtFeature, itFeature);
            }

            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIds);
            filterUnitAndPlanStatus(unitObjects, CommonStatus.VALID);

            List<Long> creativeIds = DataTable.of(AdCreativeUnitIndex.class).selectCreativeIds(unitObjects);
            List<AdCreativeObject> creativeObjects = DataTable.of(AdCreativeIndex.class).fetch(creativeIds);

            // 通过AdSlot实现对CreativeObject的过滤
            filterCreativeByAdSlot(creativeObjects, adSlot.getWidth(), adSlot.getHeight(), adSlot.getMaterialType());

            adSlot2Creative.put(adSlot.getAdSlotCode(), buildCreativeResponse(creativeObjects));
        }

        log.info("fetchCreatives: {}-{}", JSON.toJSONString(searchRequest), JSON.toJSONString(searchResponse));

        return searchResponse;
    }

    /**
     * 连接关系是Or时 过滤unitId
     * @param unitIds
     * @param keywordFeature
     * @param districtFeature
     * @param itFeature
     * @return
     */
    private Set<Long> getORRelationUnitIds(Set<Long> unitIds, KeywordFeature keywordFeature, DistrictFeature districtFeature, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return Collections.emptySet();
        }
        HashSet<Long> keywordUnitIds = Sets.newHashSet(unitIds);
        HashSet<Long> districtUnitIds = Sets.newHashSet(unitIds);
        HashSet<Long> itUnitIds = Sets.newHashSet(unitIds);

        filterKeywordFeature(keywordUnitIds, keywordFeature);
        filterDistrictFeature(districtUnitIds, districtFeature);
        filterItFeature(itUnitIds, itFeature);

        return new HashSet<>(CollectionUtils.union(CollectionUtils.union(keywordUnitIds, districtUnitIds), itUnitIds));
    }

    /**
     * 根据过滤关键字
     * @param unitIds
     * @param feature
     */
    private void filterKeywordFeature(Collection<Long> unitIds, KeywordFeature feature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(feature.getKeywords())) {
            CollectionUtils.filter(unitIds, unitId -> DataTable.of(AdUnitKeywordIndex.class).match(unitId, feature.getKeywords()));
        }
    }

    /**
     * 根据兴趣过滤
     * @param unitIds
     * @param feature
     */
    private void filterItFeature(Collection<Long> unitIds, ItFeature feature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(feature.getIts())) {
            CollectionUtils.filter(unitIds, unitId -> DataTable.of(AdUnitItIndex.class).match(unitId, feature.getIts()));
        }
    }

    /**
     * 根据地域过滤
     * @param unitIds
     * @param feature
     */
    private void filterDistrictFeature(Collection<Long> unitIds, DistrictFeature feature) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(feature.getDistricts())) {
            CollectionUtils.filter(unitIds, unitId -> DataTable.of(AdUnitDistrictIndex.class).match(unitId, feature.getDistricts()));
        }
    }

    /**
     * 根据状态过滤推广单元
     * @param unitObjects
     * @param status
     */
    private void filterUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(unitObjects, unitObject -> unitObject.getAdPlanObject().getPlanStatus().equals(status.getStatus()) && unitObject.getUnitStatus().equals(status.getStatus()));
    }

    /**
     * 根据AdSlot对创意集合过滤
     * @param creativeObjects
     * @param width
     * @param height
     * @param type
     */
    private void filterCreativeByAdSlot(List<AdCreativeObject> creativeObjects, Integer width, Integer height, List<Integer> type) {
        if (CollectionUtils.isEmpty(creativeObjects)) {
            return;
        }

        CollectionUtils.filter(creativeObjects, creativeObject ->
            creativeObject.getWidth().equals(width) && creativeObject.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                    && creativeObject.getHeight().equals(height) && type.contains(creativeObject.getType())
        );
    }

    /**
     * 随机返回一个创意
     * @param creativeObjects
     * @return
     */
    private List<SearchResponse.Creative> buildCreativeResponse(List<AdCreativeObject> creativeObjects) {
        if (CollectionUtils.isEmpty(creativeObjects))
            return Collections.emptyList();

        AdCreativeObject randomObject = creativeObjects.get(Math.abs(new Random().nextInt() % creativeObjects.size()));
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }


}
