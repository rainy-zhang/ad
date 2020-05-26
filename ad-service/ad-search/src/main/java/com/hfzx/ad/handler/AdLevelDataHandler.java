package com.hfzx.ad.handler;

import com.google.common.collect.Sets;
import com.hfzx.ad.dump.table.*;
import com.hfzx.ad.index.DataTable;
import com.hfzx.ad.index.IndexAware;
import com.hfzx.ad.index.adplan.AdPlanIndex;
import com.hfzx.ad.index.adplan.AdPlanObject;
import com.hfzx.ad.index.adunit.AdUnitIndex;
import com.hfzx.ad.index.adunit.AdUnitObject;
import com.hfzx.ad.index.creative.AdCreativeIndex;
import com.hfzx.ad.index.creative.AdCreativeObject;
import com.hfzx.ad.index.creative_unit.AdCreativeUnitIndex;
import com.hfzx.ad.index.creative_unit.AdCreativeUnitObject;
import com.hfzx.ad.index.district.AdUnitDistrictIndex;
import com.hfzx.ad.index.interest.AdUnitItIndex;
import com.hfzx.ad.index.keyword.AdUnitKeywordIndex;
import com.hfzx.ad.mysql.constant.OpType;
import com.hfzx.ad.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Set;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/1 19:15
 */
@Slf4j
public class AdLevelDataHandler {

    /**
     * 构建第二层级索引
     * @param planTable
     * @param opType
     */
    public static void handleLevel2(AdPlanTable planTable, OpType opType) {
        AdPlanObject planObject = AdPlanObject.builder()
                .planId(planTable.getId())
                .planStatus(planTable.getPlanStatus())
                .startDate(planTable.getStartDate())
                .endDate(planTable.getEndDate())
                .build();

        handleBinlogEvent(DataTable.of(AdPlanIndex.class), planObject.getPlanId(), planObject, opType);
    }

    public static void handleLevel2(AdCreativeTable creativeTable, OpType opType) {
        AdCreativeObject creativeObject = AdCreativeObject.builder()
                .creativeId(creativeTable.getCreativeId())
                .auditStatus(creativeTable.getAuditStatus())
                .type(creativeTable.getType())
                .name(creativeTable.getName())
                .materialType(creativeTable.getMaterialType())
                .width(creativeTable.getWidth())
                .height(creativeTable.getHeight())
                .url(creativeTable.getAdUrl())
                .build();

        handleBinlogEvent(DataTable.of(AdCreativeIndex.class), creativeObject.getCreativeId(), creativeObject, opType);
    }

    public static void handleLevel3(AdUnitTable unitTable, OpType opType) {
        AdPlanObject planObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if (planObject == null) {
            log.error("handleLevel3: not found AdPlanObject -> {}", unitTable.getPlanId());
            return;
        }

        AdUnitObject unitObject = AdUnitObject.builder()
                .unitId(unitTable.getUnitId())
                .planId(unitTable.getPlanId())
                .positionType(unitTable.getPositionType())
                .unitStatus(unitTable.getUnitStatus())
                .adPlanObject(planObject)
                .build();

        handleBinlogEvent(DataTable.of(AdUnitIndex.class), unitObject.getUnitId(), unitObject, opType);
    }

    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType opType) {
        if (opType == OpType.UPDATE) {
            log.error("handleLevel3: CreativeUnitIndex can not support update");
            return;
        }

        AdCreativeObject creativeObject = DataTable.of(AdCreativeIndex.class).get(creativeUnitTable.getCreativeId());
        if (null == creativeObject) {
            log.error("handleLevel3: not found AdCreativeObject -> {}", creativeUnitTable.getCreativeId());
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        if (null == unitObject) {
            log.error("handleLevel3: not found AdUnitObject -> {}", creativeUnitTable.getUnitId());
            return;
        }
        AdCreativeUnitObject adCreativeUnitObject = AdCreativeUnitObject.builder()
                .creativeId(creativeUnitTable.getCreativeId())
                .unitId(creativeUnitTable.getUnitId())
                .build();

        handleBinlogEvent(DataTable.of(AdCreativeUnitIndex.class),
                CommonUtils.stringConcat(adCreativeUnitObject.getCreativeId().toString(), adCreativeUnitObject.getUnitId().toString()),
                adCreativeUnitObject,
                opType);
    }

    @SuppressWarnings("all")
    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType opType) {
        if (opType == OpType.UPDATE) {
            log.error("handleLevel4: UnitDistrictIndex can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if (null == unitObject) {
            log.error("handleLevel4: not found AdUnitObject -> {}", unitDistrictTable.getUnitId());
            return;
        }

        String key = CommonUtils.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = Sets.newHashSet(Collections.singleton(unitDistrictTable.getUnitId()));
        handleBinlogEvent(DataTable.of(AdUnitDistrictIndex.class), key, value, opType);
    }

    @SuppressWarnings("all")
    public static void handleLevel4(AdUnitItTable unitItTable, OpType opType) {
        if (opType == OpType.UPDATE) {
            log.error("handleLevel4: UnitItIndex can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if (null == unitObject) {
            log.error("handleLevel4: not found AdUnitObject -> {}", unitItTable.getUnitId());
            return;
        }

        Set<Long> value = Sets.newHashSet(Collections.singleton(unitItTable.getUnitId()));

        handleBinlogEvent(DataTable.of(AdUnitItIndex.class), unitItTable.getItTag(), value, opType);
    }

    @SuppressWarnings("all")
    public static void handleLevel4(AdUnitKeywordTable unitKeywordTable, OpType opType) {
        if (opType == OpType.UPDATE) {
            log.error("handleLevel4: UnitKeywordIndex can not support update");
            return;
        }

        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitKeywordTable.getUnitId());
        if (null == unitObject) {
            log.error("handleLevel4: not found AdUnitObject -> {}", unitKeywordTable.getUnitId());
            return;
        }

        Set<Long> value = Sets.newHashSet(Collections.singleton(unitKeywordTable.getUnitId()));

        handleBinlogEvent(DataTable.of(AdUnitKeywordIndex.class), unitKeywordTable.getKeyword(), value, opType);
    }


    private static <K, V> void handleBinlogEvent(IndexAware<K, V> indexAware, K key, V value, OpType opType) {
        switch (opType) {
            case ADD:
                indexAware.add(key, value);
                break;
            case UPDATE:
                indexAware.update(key, value);
                break;
            case DELETE:
                indexAware.delete(key, value);
                break;
            default:
                break;
        }
    }

}
