package com.hfzx.ad.index.creative_unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 13:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeUnitObject {

    private Long creativeId;
    private Long unitId;

}
