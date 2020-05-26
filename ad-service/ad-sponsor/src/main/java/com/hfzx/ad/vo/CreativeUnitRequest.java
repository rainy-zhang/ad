package com.hfzx.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 21:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitRequest {

    private List<CreativeUnitItem> creativeUnitItems;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreativeUnitItem {
        private Long creativeId;
        private Long unitId;
    }


}
