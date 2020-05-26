package com.hfzx.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 13:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeObject {

    private Long creativeId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer auditStatus;
    private Integer width;
    private Integer height;
    private String url;

    public void update(AdCreativeObject newCreativeObject) {
        BeanUtils.copyProperties(newCreativeObject, this);
    }

}
