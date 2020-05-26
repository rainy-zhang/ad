package com.hfzx.ad.index.interest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 13:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItObject {

    private Long unitId;
    private String itTag;

}
