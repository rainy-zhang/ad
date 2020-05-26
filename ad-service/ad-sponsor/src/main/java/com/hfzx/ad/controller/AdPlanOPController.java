package com.hfzx.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.entity.AdPlan;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.service.IAdPlanService;
import com.hfzx.ad.vo.AdPlanGetRequest;
import com.hfzx.ad.vo.AdPlanRequest;
import com.hfzx.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 推广计划Controller
 * @Date: in 2020/4/23 10:33
 */
@Slf4j
@RestController
public class AdPlanOPController {

    private final IAdPlanService adPlanService;

    @Autowired
    public AdPlanOPController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

    @PostMapping(value = "/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest planRequest) throws AdException {
        log.info("ad-sponsor: createAdPlan -> {}", JSON.toJSONString(planRequest));
        return adPlanService.createAdPlan(planRequest);
    }

    @GetMapping(value = "/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest planGetRequest) throws AdException {
        log.info("ad-sponsor: getAdPlanByIds -> {}", JSON.toJSONString(planGetRequest));
        return adPlanService.getAdPlanByIds(planGetRequest);
    }

    @PutMapping(value = "/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest planRequest) throws AdException {
        log.info("ad-sponsor: updateAdPlan -> {}", JSON.toJSONString(planRequest));
        return adPlanService.updateAdPlan(planRequest);
    }

    @DeleteMapping(value = "/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest planRequest) throws AdException {
        log.info("ad-sponsor: deleteAdPlan -> {}", JSON.toJSONString(planRequest));
        adPlanService.deleteAdPlan(planRequest);
    }

    @GetMapping(value = "/getAll/adPlan")
    public List<AdPlan> getAllAdPlanByStatus(@RequestParam(value = "planStatus") Integer planStatus) throws AdException {
        log.info("ad-sponsor: getAllAdPlanByStatus -> {}", planStatus);
        return adPlanService.findAllByPlanStatus(planStatus);
    }


}
