package com.hfzx.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 9:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;
    private Integer positionType;
    private Long budget;

    public boolean createValidate() {
        return planId != null && StringUtils.isNotEmpty(unitName) && positionType != null && budget != null;
    }

}
