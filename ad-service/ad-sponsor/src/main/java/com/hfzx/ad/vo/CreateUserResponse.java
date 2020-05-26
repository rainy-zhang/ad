package com.hfzx.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 9:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse {

    private Long userId;
    private String username;
    private String token;
    private Date createTime;
    private Date updateTime;

}
