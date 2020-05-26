package com.hfzx.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 9:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictRequest {

    private List<UnitDistrict> unitDistricts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitDistrict {
        private Long unitId;
        private String province;
        private String city;
    }

}
