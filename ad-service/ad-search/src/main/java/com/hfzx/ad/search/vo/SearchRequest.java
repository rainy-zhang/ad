package com.hfzx.ad.search.vo;

import com.hfzx.ad.search.vo.feature.DistrictFeature;
import com.hfzx.ad.search.vo.feature.FeatureRelation;
import com.hfzx.ad.search.vo.feature.ItFeature;
import com.hfzx.ad.search.vo.feature.KeywordFeature;
import com.hfzx.ad.search.vo.media.AdSlot;
import com.hfzx.ad.search.vo.media.App;
import com.hfzx.ad.search.vo.media.Device;
import com.hfzx.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 检索请求实体类
 * @Date: in 2020/5/11 19:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private String mediaId;  //媒体方的请求标识

    private RequestInfo requestInfo; // 请求基本信息

    private FeatureInfo featureInfo; //匹配信息


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo { // 请求所携带的基本信息

        private String requestId;

        private List<AdSlot> adSlots; // 请求的广告位
        private App app;  //请求的应用信息
        private Geo geo;  // 请求的地理位置信息
        private Device device; // 请求的设备信息

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo { // 匹配信息
        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;
        private FeatureRelation featureRelation = FeatureRelation.AND;
    }

}
