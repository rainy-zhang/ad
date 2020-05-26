package com.hfzx.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/24 9:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlan {

    private Long id;
    private Long userId;
    private String planName;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
    private Date createTime;
    private Date updateTime;

}
