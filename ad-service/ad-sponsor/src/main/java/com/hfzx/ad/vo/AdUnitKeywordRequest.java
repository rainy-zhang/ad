package com.hfzx.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 9:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordRequest {

    private List<UnitKeyword> unitKeywords;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitKeyword {
        private Long unitId;
        private String keyword;
    }

}
