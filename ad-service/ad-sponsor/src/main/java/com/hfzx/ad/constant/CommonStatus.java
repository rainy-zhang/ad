package com.hfzx.ad.constant;

import lombok.Getter;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/21 22:06
 */
@Getter
public enum CommonStatus {

    VALID(1, "有效"),
    INVALID(0, "无效");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
