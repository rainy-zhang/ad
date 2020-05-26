package com.hfzx.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.service.IAdCreativeService;
import com.hfzx.ad.vo.AdCreativeRequest;
import com.hfzx.ad.vo.AdCreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangyu
 * @Description: 创意Controller
 * @Date: in 2020/4/23 11:16
 */
@Slf4j
@RestController
public class AdCreativeOPController {

    private final IAdCreativeService creativeService;

    @Autowired
    public AdCreativeOPController(IAdCreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping(value = "/create/adCreative")
    public AdCreativeResponse createAdCreative(@RequestBody AdCreativeRequest creativeRequest) throws AdException {
        log.info("ad-sponsor: createAdCreative -> {}", JSON.toJSONString(creativeRequest));
        return creativeService.createCreative(creativeRequest);
    }

}
