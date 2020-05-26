package com.hfzx.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.annotation.IgnoreResponseAdvice;
import com.hfzx.ad.client.SponsorClient;
import com.hfzx.ad.client.vo.AdPlan;
import com.hfzx.ad.client.vo.AdPlanGetRequest;
import com.hfzx.ad.search.ISearch;
import com.hfzx.ad.search.vo.SearchRequest;
import com.hfzx.ad.search.vo.SearchResponse;
import com.hfzx.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 测试Ribbon与Feign
 * @Date: in 2020/4/24 9:14
 */
@Slf4j
@RestController
public class SearchController {

    private final ISearch search;

    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    @Autowired
    @SuppressWarnings("all")
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient, ISearch search) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
        this.search = search;
    }

    @PostMapping("/fetchCreatives")
    public SearchResponse fetchCreatives(@RequestBody SearchRequest searchRequest) {
        log.info("ad-search: fetchCreatives -> {}", JSON.toJSONString(searchRequest));
        return search.fetchCreatives(searchRequest);
    }

    @IgnoreResponseAdvice
    @GetMapping("/get/adPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest planGetRequest) {
        log.info("ad-search: getAdPlans -> {}", JSON.toJSONString(planGetRequest));
        return sponsorClient.getAdPlans(planGetRequest);
    }

    @SuppressWarnings("all")
    @GetMapping("/get/adPlansByRibbon")
    @IgnoreResponseAdvice
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest planGetRequest) {
        log.info("ad-search: getAdPlansByRibbon -> {}", JSON.toJSONString(planGetRequest));
        return restTemplate.getForObject("eureka-client-ad-sponsor", CommonResponse.class, planGetRequest);
    }

}
