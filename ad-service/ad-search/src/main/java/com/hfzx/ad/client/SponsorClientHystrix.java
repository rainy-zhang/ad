package com.hfzx.ad.client;

import com.hfzx.ad.client.vo.AdPlan;
import com.hfzx.ad.client.vo.AdPlanGetRequest;
import com.hfzx.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/24 9:11
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest planGetRequest) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
