package com.hfzx.ad.dao.unit_condition;

import com.hfzx.ad.entity.unit_condition.AdUnitKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: zhangyu
 * @Description: 关键字限制维度
 * @Date: in 2020/4/22 8:54
 */
public interface AdUnitKeywordRepository extends JpaRepository<AdUnitKeyword, Long> {

}
