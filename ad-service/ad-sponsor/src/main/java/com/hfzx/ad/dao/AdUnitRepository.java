package com.hfzx.ad.dao;

import com.hfzx.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 推广单元Dao
 * @Date: in 2020/4/21 23:11
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    /**
     * <h2>根据推广计划Id和推广单元名称查询推广单元</h2>
     * @param planId
     * @param unitName
     * @return
     */
    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    /**
     * <h2>根据状态批量查询推广单元</h2>
     * @param unitStatus
     * @return
     */
    List<AdUnit> findAllByUnitStatus(Integer unitStatus);

}
