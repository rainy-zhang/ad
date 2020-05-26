package com.hfzx.ad.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 11:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordObject {

    private Long unitId;
    private String keyword;

}
