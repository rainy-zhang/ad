package com.hfzx.ad.service;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.Application;
import com.hfzx.ad.entity.AdPlan;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.vo.AdPlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/22 20:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {

    @Autowired
    private IAdPlanService planService;

    @Test
    public void testGetAdPlan() throws AdException {
        List<AdPlan> adplanList = planService.getAdPlanByIds(new AdPlanGetRequest(15L, Collections.singletonList(10L)));
        System.out.println(JSON.toJSONString(adplanList));

    }

}
