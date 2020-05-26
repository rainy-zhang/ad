package com.hfzx.ad.sender.index;

import com.google.common.collect.Lists;
import com.hfzx.ad.dump.table.*;
import com.hfzx.ad.handler.AdLevelDataHandler;
import com.hfzx.ad.index.DataLevel;
import com.hfzx.ad.mysql.constant.Constant;
import com.hfzx.ad.mysql.dto.MySqlRowData;
import com.hfzx.ad.sender.BinlogSender;
import com.hfzx.ad.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyu
 * @Description: 投递索引数据
 * @Date: in 2020/5/10 17:22
 */
@Slf4j
@Component(value = "indexSender")
public class IndexSender implements BinlogSender {

    @Override
    public void send(MySqlRowData mySqlRowData) {
        String level = mySqlRowData.getLevel();
        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            level2RowData(mySqlRowData);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {
            level3RowData(mySqlRowData);
        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {
            level4RowData(mySqlRowData);
        } else {
            log.error("IndexSender unknown level: {}", level);
        }
    }


    /**
     * 投递第二层级索引
     * @param mySqlRowData
     */
    private void level2RowData(MySqlRowData mySqlRowData) {
        // 投递推广计划索引
        if (Constant.AD_PLAN_TABLE_INFO.TABLE_NAME.equals(mySqlRowData.getTableName())) {
            List<AdPlanTable> planTables = Lists.newArrayList();
            for (Map<String, String> fieldMap : mySqlRowData.getFieldValueMap()) {
                AdPlanTable planTable = new AdPlanTable();
                fieldMap.forEach((key, value) -> {
                    switch (key) {
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID:
                            planTable.setId(Long.valueOf(value));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(value));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(value));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE:
                            planTable.setStartDate(CommonUtils.parseStringDate(value));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE:
                            planTable.setEndDate(CommonUtils.parseStringDate(value));
                            break;
                    }
                });
                planTables.add(planTable);
            }
            planTables.forEach(item -> AdLevelDataHandler.handleLevel2(item, mySqlRowData.getOpType()));

        } else if (Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME.equals(mySqlRowData.getTableName())) { // 投递创意索引
            List<AdCreativeTable> creativeTables = Lists.newArrayList();
            for (Map<String, String> fieldMap : mySqlRowData.getFieldValueMap()) {
                AdCreativeTable creativeTable = new AdCreativeTable();
                fieldMap.forEach((key, value) -> {
                    switch (key) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setCreativeId(Long.valueOf(value));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(value));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(value));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(value));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(value));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(value));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setAdUrl(value);
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach(creative -> AdLevelDataHandler.handleLevel2(creative, mySqlRowData.getOpType()));
        }
    }


    /**
     * 投递第三层级索引
     * @param mySqlRowData
     */
    private void level3RowData(MySqlRowData mySqlRowData) {
        // 投递推广单元索引
        if (Constant.AD_UNIT_TABLE_INFO.TABLE_NAME.equals(mySqlRowData.getTableName())) {
            List<AdUnitTable> unitTables = Lists.newArrayList();
            for (Map<String, String> fieldMap : mySqlRowData.getFieldValueMap()) {
                AdUnitTable unitTable = new AdUnitTable();
                fieldMap.forEach((key, value) -> {
                    switch (key) {
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            unitTable.setUnitId(Long.valueOf(value));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(value));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(value));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(value));
                            break;
                    }
                });
                unitTables.add(unitTable);
            }
            unitTables.forEach(unit -> AdLevelDataHandler.handleLevel3(unit, mySqlRowData.getOpType()));
        } else if (Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME.equals(mySqlRowData.getTableName())) { // 投递创意与推广单元关联关系索引
            List<AdCreativeUnitTable> creativeUnitTables = Lists.newArrayList();
            for (Map<String, String> fieldMap : mySqlRowData.getFieldValueMap()) {
                AdCreativeUnitTable creativeUnitTable = new AdCreativeUnitTable();
                fieldMap.forEach((key, value) -> {
                    switch (key) {
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setCreativeId(Long.valueOf(value));
                            break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(value));
                            break;
                    }
                });
                creativeUnitTables.add(creativeUnitTable);
            }
            creativeUnitTables.forEach(creativeUnitTable -> AdLevelDataHandler.handleLevel3(creativeUnitTable, mySqlRowData.getOpType()));
        }
    }


    /**
     * 投递第四层级索引
     * @param mySqlRowData
     */
    private void level4RowData(MySqlRowData mySqlRowData) {
        switch (mySqlRowData.getTableName()) {
            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:  //投递地域限制维度索引
                List<AdUnitDistrictTable> unitDistrictTables = Lists.newArrayList();
                for (Map<String, String> fieldValueMap : mySqlRowData.getFieldValueMap()) {
                    AdUnitDistrictTable unitDistrictTable = new AdUnitDistrictTable();
                    fieldValueMap.forEach((key, value) -> {
                        switch (key) {
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                unitDistrictTable.setUnitId(Long.valueOf(value));
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                unitDistrictTable.setProvince(value);
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                unitDistrictTable.setCity(value);
                                break;
                        }
                    });
                    unitDistrictTables.add(unitDistrictTable);
                }
                unitDistrictTables.forEach(unitDistrictTable -> AdLevelDataHandler.handleLevel4(unitDistrictTable, mySqlRowData.getOpType()));
                break;
            case Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME: //投递兴趣限制维度索引
                List<AdUnitItTable> unitItTables = Lists.newArrayList();
                for (Map<String, String> fieldValueMap : mySqlRowData.getFieldValueMap()) {
                    AdUnitItTable unitItTable = new AdUnitItTable();
                    fieldValueMap.forEach((key, value) -> {
                        switch (key) {
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                                unitItTable.setUnitId(Long.valueOf(value));
                                break;
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                                unitItTable.setItTag(value);
                                break;
                        }
                    });
                    unitItTables.add(unitItTable);
                }
                unitItTables.forEach(unitItTable -> AdLevelDataHandler.handleLevel4(unitItTable, mySqlRowData.getOpType()));
                break;
            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME: //投递关键词纬度索引
                List<AdUnitKeywordTable> unitKeywordTables = Lists.newArrayList();
                for (Map<String, String> fieldValueMap : mySqlRowData.getFieldValueMap()) {
                    AdUnitKeywordTable unitKeywordTable = new AdUnitKeywordTable();
                    fieldValueMap.forEach((key, value) -> {
                        switch (key) {
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                unitKeywordTable.setUnitId(Long.valueOf(value));
                                break;
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                unitKeywordTable.setKeyword(value);
                                break;
                        }
                    });
                    unitKeywordTables.add(unitKeywordTable);
                }
                unitKeywordTables.forEach(unitKeywordTable -> AdLevelDataHandler.handleLevel4(unitKeywordTable, mySqlRowData.getOpType()));
                break;
        }
    }


}
