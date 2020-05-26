package com.hfzx.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 广告位信息
 * @Date: in 2020/5/11 19:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    // 广告位编码
    private String adSlotCode;

    // 流量类型
    private Integer positionType;

    // 宽高
    private Integer width;
    private Integer height;

    // 物料类型, 可以包含多个类型
    private List<Integer> materialType;

    // 最低出价
    private Integer minCpm;

}
