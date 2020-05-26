package com.hfzx.ad.service;

import com.hfzx.ad.exception.AdException;
import com.hfzx.ad.vo.AdCreativeRequest;
import com.hfzx.ad.vo.AdCreativeResponse;

/**
 * @Author: zhangyu
 * @Description: 创意Service
 * @Date: in 2020/4/22 9:29
 */
public interface IAdCreativeService {

    /**
     * <h2>创建创意</h2>
     * @param request
     * @return
     * @throws AdException
     */
    AdCreativeResponse createCreative(AdCreativeRequest request) throws AdException;

}
