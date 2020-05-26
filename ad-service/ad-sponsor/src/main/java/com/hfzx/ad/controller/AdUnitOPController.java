package com.hfzx.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.service.IAdUnitService;
import com.hfzx.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangyu
 * @Description: 推广单元Controller
 * @Date: in 2020/4/23 11:04
 */
@Slf4j
@RestController
public class AdUnitOPController {

    private final IAdUnitService adUnitService;

    @Autowired
    public AdUnitOPController(IAdUnitService adUnitService) {
        this.adUnitService = adUnitService;
    }

    @PostMapping(value = "/create/adUnit")
    public AdUnitResponse createAdUnit(@RequestBody AdUnitRequest unitRequest) throws AdException {
        log.info("ad-sponsor: createAdUnit -> {}", JSON.toJSONString(unitRequest));
        return adUnitService.createUnit(unitRequest);
    }

    @PostMapping(value = "/create/unitKeyword")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest keywordRequest) throws AdException {
        log.info("ad-sponsor: createUnitKeyword -> {}", JSON.toJSONString(keywordRequest));
        return adUnitService.createUnitKeyword(keywordRequest);
    }

    @PostMapping(value = "/create/unitDistrict")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest districtRequest) throws AdException {
        log.info("ad-sponsor: createUnitDistrict -> {}", JSON.toJSONString(districtRequest));
        return adUnitService.createUnitDistrict(districtRequest);
    }

    @PostMapping(value = "/create/unitIt")
    public AdUnitItResponse createIt(@RequestBody AdUnitItRequest itRequest) throws AdException {
        log.info("ad-sponsor: createId -> {}", JSON.toJSONString(itRequest));
        return adUnitService.createUnitIt(itRequest);
    }




}
