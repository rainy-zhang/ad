package com.hfzx.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 9:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdCreativeResponse {

    private Long id;
    private String name;

}
