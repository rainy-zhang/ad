package com.hfzx.ad.search;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.Application;
import com.hfzx.ad.search.vo.SearchRequest;
import com.hfzx.ad.search.vo.feature.DistrictFeature;
import com.hfzx.ad.search.vo.feature.FeatureRelation;
import com.hfzx.ad.search.vo.feature.ItFeature;
import com.hfzx.ad.search.vo.feature.KeywordFeature;
import com.hfzx.ad.search.vo.media.AdSlot;
import com.hfzx.ad.search.vo.media.App;
import com.hfzx.ad.search.vo.media.Device;
import com.hfzx.ad.search.vo.media.Geo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/22 20:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch search;

    @Test
    public void testFetchCreatives() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setMediaId("hfzx-ad");

        searchRequest.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马", "大众"),
                Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省", "合肥市")),
                Arrays.asList("台球", "游泳"),
                FeatureRelation.OR
        ));
        searchRequest.setRequestInfo(new SearchRequest.RequestInfo(
                "aaa",
                Collections.singletonList(new AdSlot("ad-x", 1, 1080, 720, Arrays.asList(1,2), 10000)),
                buildExampleApp(),
                buildExampeGeo(),
                buildExampleDevice()
        ));
        System.out.println(JSON.toJSONString(searchRequest));
        System.out.println(JSON.toJSONString(search.fetchCreatives(searchRequest)));
    }

    private App buildExampleApp() {
        return new App("hfzx", "hfzx", "com.hfzx", "video");
    }

    private Geo buildExampeGeo() {
        return new Geo((float) 100.28, (float) 88.12, "合肥市", "安徽省");
    }

    private Device buildExampleDevice() {
        return new Device("iphone", "0xxxxaaa", "127.0.0.1", "x", "1080 720", "1080 720", "123456");
    }

    private SearchRequest.FeatureInfo buildExampleFeatureInfo(List<String> keywords, List<DistrictFeature.ProvinceAndCity> provinceAndCities, List<String> its, FeatureRelation relation) {
        return new SearchRequest.FeatureInfo(new KeywordFeature(keywords), new DistrictFeature(provinceAndCities), new ItFeature(its), relation);
    }

}
