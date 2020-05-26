package com.hfzx.ad.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: zhangyu
 * @Description: 推广计划索引实体类
 * @Date: in 2020/4/25 10:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanObject {

    private Long planId;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;

    public void update(AdPlanObject newPlanObject) {
        if (null != newPlanObject.getPlanId())
            this.planId = newPlanObject.getPlanId();
        if (null != newPlanObject.getUserId())
            this.userId = newPlanObject.getUserId();
        if (null != newPlanObject.getPlanStatus())
            this.planStatus = newPlanObject.getPlanStatus();
        if (null != newPlanObject.getStartDate())
            this.startDate = newPlanObject.getStartDate();
        if (null != newPlanObject.getEndDate())
            this.endDate = newPlanObject.getEndDate();
    }

}
