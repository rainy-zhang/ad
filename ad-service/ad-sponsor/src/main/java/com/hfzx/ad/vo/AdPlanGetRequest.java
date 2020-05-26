package com.hfzx.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 9:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanGetRequest {

    private Long userId;
    private List<Long> ids;

    public boolean validate() {
        return userId != null && !CollectionUtils.isEmpty(ids);
    }

}
