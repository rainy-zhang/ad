package com.hfzx.ad.client;

import com.hfzx.ad.client.vo.AdPlan;
import com.hfzx.ad.client.vo.AdPlanGetRequest;
import com.hfzx.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/23 21:34
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.GET)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest planGetRequest);

}
