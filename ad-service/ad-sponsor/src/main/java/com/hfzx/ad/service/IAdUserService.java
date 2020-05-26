package com.hfzx.ad.service;

import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.vo.CreateUserRequest;
import com.hfzx.ad.vo.CreateUserResponse;

/**
 * @Author: zhangyu
 * @Description: 用户Service
 * @Date: in 2020/4/22 9:17
 */
public interface IAdUserService {

    /**
     * 创建用户
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;

}
