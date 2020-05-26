package com.hfzx.ad.search.vo;

import com.google.common.collect.Maps;
import com.hfzx.ad.index.creative.AdCreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyu
 * @Description: 检索响应实体类
 * @Date: in 2020/5/11 19:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    /**
     * key: adSlotCode 广告位的编码, value: List<Creative>
     */
    private Map<String, List<Creative>> adSlot2Creative = Maps.newHashMap();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {
        private Long creativeId;
        private String url;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        // 展示监测url
        private List<String> showMonitorUrl = Arrays.asList("www.imooc.com", "www.baidu.com");
        // 点击监测url
        private List<String> clickMonitorUrl = Arrays.asList("www.imooc.com", "www.baidu.com");
    }


    /**
     * 把定义的索引对象(AdCreativeObject)转换为Creative对象
     */
    public static Creative convert(AdCreativeObject object) {
        Creative creative = new Creative();
        BeanUtils.copyProperties(object, creative);
        return creative;
    }

}
