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
public class AdCreativeTable {

    private Long creativeId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String adUrl;
}
