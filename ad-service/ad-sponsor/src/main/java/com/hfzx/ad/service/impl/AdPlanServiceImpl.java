package com.hfzx.ad.service.impl;

import com.hfzx.ad.constant.CommonStatus;
import com.hfzx.ad.constant.Constants;
import com.hfzx.ad.dao.AdPlanRepository;
import com.hfzx.ad.dao.AdUserRepository;
import com.hfzx.ad.entity.AdPlan;
import com.hfzx.ad.entity.AdUser;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.service.IAdPlanService;
import com.hfzx.ad.util.CommonUtils;
import com.hfzx.ad.vo.AdPlanGetRequest;
import com.hfzx.ad.vo.AdPlanRequest;
import com.hfzx.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 13:25
 */
@Slf4j
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdPlanRepository planRepository;
    private final AdUserRepository userRepository;

    @Autowired
    public AdPlanServiceImpl(AdPlanRepository planRepository, AdUserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        // 确保关联的User存在
        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FOUND_RECORD);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        AdPlan newPlan = planRepository.save(new AdPlan(
                request.getUserId(),
                request.getPlanName(),
                CommonUtils.parseStringToDate(request.getStartDate()),
                CommonUtils.parseStringToDate(request.getEndDate())
        ));

        return new AdPlanResponse(newPlan.getId(), newPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan oldPlan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (oldPlan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FOUND_RECORD);
        }

        if (request.getPlanName() != null) {
            oldPlan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            oldPlan.setStartDate(CommonUtils.parseStringToDate(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            oldPlan.setEndDate(CommonUtils.parseStringToDate(request.getEndDate()));
        }
        oldPlan.setUpdateTime(new Date());
        AdPlan newPlan = planRepository.save(oldPlan);
        return new AdPlanResponse(newPlan.getId(), newPlan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan oldPlan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (null == oldPlan) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FOUND_RECORD);
        }
        oldPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
        oldPlan.setUpdateTime(new Date());
        planRepository.save(oldPlan);
    }

    @Override
    public List<AdPlan> findAllByPlanStatus(Integer status) throws AdException {
        return planRepository.findAllByPlanStatus(status);
    }


}
