package com.hfzx.ad.service;

import com.hfzx.ad.entity.AdPlan;
import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.vo.AdPlanGetRequest;
import com.hfzx.ad.vo.AdPlanRequest;
import com.hfzx.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 推广计划Service
 * @Date: in 2020/4/22 9:00
 */
public interface IAdPlanService {

    /**
     * <h2>创建推广计划</h2>
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * <h2>获取推广计划</h2>
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * <h2>更新推广计划</h2>
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * <h2>删除推广计划</h2>
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;

    List<AdPlan> findAllByPlanStatus(Integer status) throws AdException;
}
