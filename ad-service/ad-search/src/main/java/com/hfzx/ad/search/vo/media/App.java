package com.hfzx.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhangyu
 * @Description: 应用的信息
 * @Date: in 2020/5/11 19:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

    // 应用编码
    private String appCode;

    // 应用名称
    private String appName;

    // 应用包名
    private String packageName;

    // 应用的请求页面名称
    private String activityName;

}
