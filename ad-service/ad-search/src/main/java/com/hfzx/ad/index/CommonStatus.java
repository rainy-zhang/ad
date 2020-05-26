package com.hfzx.ad.index;

import lombok.Getter;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/14 20:40
 */
@Getter
public enum CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}