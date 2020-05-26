package com.hfzx.ad.vo;

import com.hfzx.ad.constant.CommonStatus;
import com.hfzx.ad.entity.AdCreative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 9:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreativeRequest {

    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;

    public AdCreative convertToEntity() {
        return AdCreative.builder()
                .name(name)
                .type(type)
                .materialType(materialType)
                .height(height)
                .width(width)
                .size(size)
                .duration(duration)
                .auditStatus(CommonStatus.VALID.getStatus())
                .userId(userId)
                .url(url)
                .createTime(new Date())
                .updateTime(new Date())
                .build();
    }


}
