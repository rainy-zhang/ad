package com.hfzx.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description: 地理位置信息
 * @Date: in 2020/5/11 19:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    private Float latitude;

    private Float longitude;

    private String city;

    private String province;

}
