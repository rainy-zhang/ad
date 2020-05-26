package com.hfzx.ad.service.impl;

import com.hfzx.ad.constant.Constants;
import com.hfzx.ad.dao.AdUserRepository;
import com.hfzx.ad.entity.AdUser;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.service.IAdUserService;
import com.hfzx.ad.util.CommonUtils;
import com.hfzx.ad.vo.CreateUserRequest;
import com.hfzx.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 11:48
 */
@Slf4j
@Service
public class AdUserServiceImpl implements IAdUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public AdUserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_USERNAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(
                request.getUsername(), CommonUtils.md5(request.getUsername())
                ));

        return CreateUserResponse.builder()
                .userId(newUser.getId())
                .username(newUser.getUsername())
                .token(newUser.getToken())
                .createTime(newUser.getCreateTime())
                .updateTime(newUser.getUpdateTime())
                .build();
    }



}
