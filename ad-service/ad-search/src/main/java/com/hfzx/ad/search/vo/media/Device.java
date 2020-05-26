package com.hfzx.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description: 媒体方的设备信息
 * @Date: in 2020/5/11 19:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    // 设备编码
    private String deviceCode;

    private String mac;

    private String ip;

    // 机型编码
    private String model;

    // 分辨率尺寸
    private String displaySize;

    // 屏幕尺寸
    private String screenSize;

    // 设备序列号
    private String serialName;
}
