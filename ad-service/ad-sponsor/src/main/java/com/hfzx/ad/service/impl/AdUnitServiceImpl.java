package com.hfzx.ad.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hfzx.ad.constant.Constants;
import com.hfzx.ad.dao.AdCreativeRepository;
import com.hfzx.ad.dao.AdPlanRepository;
import com.hfzx.ad.dao.AdUnitRepository;
import com.hfzx.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.hfzx.ad.dao.unit_condition.AdUnitItRepository;
import com.hfzx.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.hfzx.ad.dao.unit_condition.CreativeUnitRepository;
import com.hfzx.ad.entity.AdPlan;
import com.hfzx.ad.entity.AdUnit;
import com.hfzx.ad.entity.unit_condition.AdUnitDistrict;
import com.hfzx.ad.entity.unit_condition.AdUnitIt;
import com.hfzx.ad.entity.unit_condition.AdUnitKeyword;
import com.hfzx.ad.entity.unit_condition.CreativeUnit;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.service.IAdUnitService;
import com.hfzx.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 15:47
 */
@Slf4j
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdUnitRepository unitRepository;
    private final AdPlanRepository planRepository;
    private final AdUnitKeywordRepository unitKeywordRepository;
    private final AdUnitItRepository unitItRepository;
    private final AdUnitDistrictRepository unitDistrictRepository;
    private final CreativeUnitRepository creativeUnitRepository;
    private final AdCreativeRepository creativeRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository, AdUnitKeywordRepository unitKeywordRepository, AdUnitItRepository unitItRepository, AdUnitDistrictRepository unitDistrictRepository, CreativeUnitRepository creativeUnitRepository, AdCreativeRepository creativeRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitItRepository = unitItRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.creativeUnitRepository = creativeUnitRepository;
        this.creativeRepository = creativeRepository;
    }

    @Override
    @Transactional
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 确保关联的推广计划存在
        Optional<AdPlan> adPlan = planRepository.findById(request.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FOUND_RECORD);
        }

        AdUnit adUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (adUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        AdUnit newUnit = unitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget()));

        return AdUnitResponse.builder()
                .id(newUnit.getId())
                .unitName(newUnit.getUnitName())
                .build();
    }

    @Override
    @Transactional
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        // 确保关联的推广单元是存在的
        List<Long> unitIds = request.getUnitDistricts().stream().map(AdUnitDistrictRequest.UnitDistrict::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds))
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);

        List<Long> ids = Collections.emptyList();
        ArrayList<AdUnitDistrict> unitDistricts = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(request.getUnitDistricts())) {
            request.getUnitDistricts().forEach(item -> {
                unitDistricts.add(
                        AdUnitDistrict.builder()
                                .unitId(item.getUnitId())
                                .province(item.getProvince())
                                .city(item.getCity())
                                .build()
                );
            });

            ids = unitDistrictRepository.saveAll(unitDistricts).stream().map(AdUnitDistrict::getId).collect(Collectors.toList());
        }

        return new AdUnitDistrictResponse(ids);
    }

    @Override
    @Transactional
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        // 确保关联的推广单元存在
        List<Long> unitIds = request.getUnitKeywords().stream().map(AdUnitKeywordRequest.UnitKeyword::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds))
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        List<Long> ids = Collections.emptyList();
        List<AdUnitKeyword> unitKeywords = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(item -> {
                unitKeywords.add(
                        AdUnitKeyword.builder()
                                .unitId(item.getUnitId())
                                .keyword(item.getKeyword())
                                .build()
                );
            });
            ids = unitKeywordRepository.saveAll(unitKeywords).stream().map(AdUnitKeyword::getId).collect(Collectors.toList());
        }

        return new AdUnitKeywordResponse(ids);
    }

    @Override
    @Transactional
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        // 确保关联的推广单元是存在的
        List<Long> unitIds = request.getUnitIts().stream().map(AdUnitItRequest.UnitIt::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds))
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);

        List<Long> ids = Collections.emptyList();
        List<AdUnitIt> unitIts = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(request.getUnitIts())) {
            request.getUnitIts().forEach(item -> {
                unitIts.add(
                        AdUnitIt.builder()
                                .unitId(item.getUnitId())
                                .itTag(item.getItTag())
                                .build()
                );
            });
            ids = unitItRepository.saveAll(unitIts).stream().map(AdUnitIt::getId).collect(Collectors.toList());
        }

        return new AdUnitItResponse(ids);
    }

    @Override
    @Transactional
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        List<Long> unitIds = request.getCreativeUnitItems().stream().map(CreativeUnitRequest.CreativeUnitItem::getUnitId).collect(Collectors.toList());
        List<Long> creativeIds = request.getCreativeUnitItems().stream().map(CreativeUnitRequest.CreativeUnitItem::getCreativeId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds) || !isRelatedCreativeExist(creativeIds))
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);

        List<CreativeUnit> creativeUnits = Lists.newArrayList();
        request.getCreativeUnitItems().forEach(item -> {
            creativeUnits.add(
                    CreativeUnit.builder()
                            .creativeId(item.getCreativeId())
                            .unitId(item.getUnitId())
                            .build()
            );
        });
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId).collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }

    /**
     * 判断推广单元是否存在
     * @param unitIds
     * @return
     */
    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds))
            return false;
        return unitRepository.findAllById(unitIds).size() == Sets.newHashSet(unitIds).size();
    }

    /**
     * 判断创意是否存在
     */
    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds))
            return false;
        return creativeRepository.findAllById(creativeIds).size() == Sets.newHashSet(creativeIds).size();
    }
}
