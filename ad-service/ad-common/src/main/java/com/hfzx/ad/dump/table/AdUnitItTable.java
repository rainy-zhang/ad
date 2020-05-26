package com.hfzx.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 20:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItTable {

    private Long unitId;
    private String itTag;
}
