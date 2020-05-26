package com.hfzx.ad.service.impl;

import com.hfzx.ad.constant.Constants;
import com.hfzx.ad.dao.AdCreativeRepository;
import com.hfzx.ad.dao.AdUserRepository;
import com.hfzx.ad.entity.AdCreative;
import com.hfzx.ad.entity.AdUser;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.service.IAdCreativeService;
import com.hfzx.ad.vo.AdCreativeRequest;
import com.hfzx.ad.vo.AdCreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 15:54
 */
@Slf4j
@Service
public class AdCreativeServiceImpl implements IAdCreativeService {

    private final AdCreativeRepository creativeRepository;
    private final AdUserRepository userRepository;

    @Autowired
    public AdCreativeServiceImpl(AdCreativeRepository creativeRepository, AdUserRepository userRepository) {
        this.creativeRepository = creativeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AdCreativeResponse createCreative(AdCreativeRequest request) throws AdException {
        // 确保关联的用户存在
        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if (!adUser.isPresent())
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);

        AdCreative newCreative = creativeRepository.save(request.convertToEntity());

        return new AdCreativeResponse(newCreative.getId(), newCreative.getName());
    }
}
