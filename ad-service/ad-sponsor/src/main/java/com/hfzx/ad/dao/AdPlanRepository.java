package com.hfzx.ad.dao;

import com.hfzx.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 推广计划Dao
 * @Date: in 2020/4/21 23:04
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    /**
     * <h2>根据id和用户id查询推广计划</h2>
     * @param id
     * @param userId
     * @return
     */
    AdPlan findByIdAndUserId(Long id, Long userId);

    /**
     * <h2>根据id列表和用户id批量查询推广计划</h2>
     * @param ids
     * @param userId
     * @return
     */
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    /**
     * <h2>根据用户id和推广计划名称查询推广计划</h2>
     * @param userId
     * @param planName
     * @return
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * <h2>根据状态批量查询推广计划</h2>
     * @param planStatus
     * @return
     */
    List<AdPlan> findAllByPlanStatus(Integer planStatus);

}
