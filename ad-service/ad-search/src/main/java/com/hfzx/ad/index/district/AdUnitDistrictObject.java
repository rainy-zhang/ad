package com.hfzx.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 13:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictObject {

    private Long unitId;
    private String province;
    private String city;

}
