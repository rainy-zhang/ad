package com.hfzx.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: zhangyu
 * @Description: 兴趣限制维度
 * @Date: in 2020/4/21 22:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ad_unit_it")
public class AdUnitIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    /** 兴趣标签 */
    @Basic
    @Column(name = "it_tag", nullable = false)
    private String itTag;

    public AdUnitIt(Long unitId, String itTag) {
        this.unitId = unitId;
        this.itTag = itTag;
    }
}
