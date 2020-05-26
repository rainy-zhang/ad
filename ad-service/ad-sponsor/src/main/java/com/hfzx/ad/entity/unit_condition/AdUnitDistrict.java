package com.hfzx.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: zhangyu
 * @Description: 区域限制维度
 * @Date: in 2020/4/21 22:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ad_unit_district")
public class AdUnitDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "province", nullable = false)
    private String province;

    @Basic
    @Column(name = "city", nullable = false)
    private String city;

    public AdUnitDistrict(Long unitId, String province, String city) {
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }
}
