package com.hfzx.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.service.IAdUserService;
import com.hfzx.ad.vo.CreateUserRequest;
import com.hfzx.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangyu
 * @Description: 用户Controller
 * @Date: in 2020/4/22 22:01
 */
@Slf4j
@RestController
public class UserOPController {

    private final IAdUserService userService;

    @Autowired
    public UserOPController(IAdUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest userRequest) throws AdException {
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(userRequest));
        return userService.createUser(userRequest);
    }

}
