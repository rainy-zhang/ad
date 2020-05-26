package com.hfzx.ad.service;

import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.vo.*;

/**
 * @Author: zhangyu
 * @Description: 推广单元Service
 * @Date: in 2020/4/22 9:32
 */
public interface IAdUnitService {

    /**
     * <h2>创建推广单元</h2>
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * <h2>创建地域限制</h2>
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;

    /**
     * <h2>创建关键字限制</h2>
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    /**
     * <h2>创建兴趣限制</h2>
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * <h2>创建创意与推广单元关联关系</h2>
     * @param request
     * @return
     * @throws AdException
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
