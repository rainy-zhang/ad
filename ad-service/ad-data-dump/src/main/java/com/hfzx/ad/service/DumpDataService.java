package com.hfzx.ad.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hfzx.ad.constant.CommonStatus;
import com.hfzx.ad.dao.AdCreativeRepository;
import com.hfzx.ad.dao.AdPlanRepository;
import com.hfzx.ad.dao.AdUnitRepository;
import com.hfzx.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.hfzx.ad.dao.unit_condition.AdUnitItRepository;
import com.hfzx.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.hfzx.ad.dao.unit_condition.CreativeUnitRepository;
import com.hfzx.ad.dump.DumpConstant;
import com.hfzx.ad.dump.table.*;
import com.hfzx.ad.entity.AdCreative;
import com.hfzx.ad.entity.AdPlan;
import com.hfzx.ad.entity.AdUnit;
import com.hfzx.ad.entity.unit_condition.AdUnitDistrict;
import com.hfzx.ad.entity.unit_condition.AdUnitIt;
import com.hfzx.ad.entity.unit_condition.AdUnitKeyword;
import com.hfzx.ad.entity.unit_condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 20:33
 */
@Slf4j
@Service
public class DumpDataService {

    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;
    private final AdCreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;
    private final AdUnitDistrictRepository districtRepository;
    private final AdUnitItRepository itRepository;
    private final AdUnitKeywordRepository keywordRepository;

    @Autowired
    public DumpDataService(AdPlanRepository planRepository, AdUnitRepository unitRepository, AdCreativeRepository creativeRepository, CreativeUnitRepository creativeUnitRepository, AdUnitDistrictRepository districtRepository, AdUnitItRepository itRepository, AdUnitKeywordRepository keywordRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
        this.districtRepository = districtRepository;
        this.itRepository = itRepository;
        this.keywordRepository = keywordRepository;
    }

    @PostConstruct
    public void dumpAdTableData() {
        dumpAdPlanTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_PLAN));
        dumpAdUnitTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR,DumpConstant.AD_UNIT));
        dumpAdCreativeTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR,DumpConstant.AD_CREATIVE));
        dumpAdCreativeUnitTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR,DumpConstant.AD_CREATIVE_UNIT));
        dumpUnitDistrictTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR,DumpConstant.AD_UNIT_DISTRICT));
        dumpUnitItTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR,DumpConstant.AD_UNIT_IT));
        dumpUnitKeywordTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR,DumpConstant.AD_UNIT_KEYWORD));
    }

    private void dumpAdPlanTable(String filename) {
        List<AdPlan> adPlanList = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if(CollectionUtils.isEmpty(adPlanList))
            return;

        ArrayList<AdPlanTable> planTableList = Lists.newArrayList();
        adPlanList.forEach(item -> planTableList.add(
                AdPlanTable.builder()
                        .id(item.getId())
                        .userId(item.getUserId())
                        .planStatus(item.getPlanStatus())
                        .startDate(item.getStartDate())
                        .endDate(item.getEndDate())
                        .build()
        ));

        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            for (AdPlanTable planTable : planTableList) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdPlanTable error");
        }
    }

    private void dumpAdUnitTable(String filename) {
        List<AdUnit> adUnitList = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adUnitList))
            return;

        ArrayList<AdUnitTable> unitTableList = Lists.newArrayList();
        adUnitList.forEach(item -> unitTableList.add(
                AdUnitTable.builder()
                        .unitId(item.getId())
                        .planId(item.getPlanId())
                        .unitStatus(item.getUnitStatus())
                        .positionType(item.getPositionType())
                        .build()
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            for (AdUnitTable unitTable : unitTableList) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdUnitTable error");
        }
    }

    private void dumpAdCreativeTable(String filename) {
        List<AdCreative> creativeList = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creativeList))
            return;

        ArrayList<AdCreativeTable> creativeTableList = Lists.newArrayList();
        creativeList.forEach(item -> creativeTableList.add(
                AdCreativeTable.builder()
                        .creativeId(item.getId())
                        .name(item.getName())
                        .adUrl(item.getUrl())
                        .auditStatus(item.getAuditStatus())
                        .height(item.getHeight())
                        .width(item.getWidth())
                        .type(item.getType())
                        .materialType(item.getMaterialType())
                        .build()
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable creativeTable : creativeTableList) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpCreativeTable error");
        }
    }

    private void dumpAdCreativeUnitTable(String filename) {
        List<CreativeUnit> creativeUnitList = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnitList))
            return;

        ArrayList<AdCreativeUnitTable> creativeUnitTableList = Lists.newArrayList();
        creativeUnitList.forEach(item -> creativeUnitTableList.add(
                AdCreativeUnitTable.builder()
                        .creativeId(item.getCreativeId())
                        .unitId(item.getUnitId())
                        .build()
        ));

        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable creativeUnitTable : creativeUnitTableList) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdCreativeUnitTable error");
        }
    }

    private void dumpUnitKeywordTable(String filename) {
        List<AdUnitKeyword> unitKeywordList = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywordList))
            return;

        ArrayList<AdUnitKeywordTable> unitKeywordTableList = Lists.newArrayList();
        unitKeywordList.forEach(item ->unitKeywordTableList.add(
                AdUnitKeywordTable.builder()
                        .unitId(item.getUnitId())
                        .keyword(item.getKeyword())
                        .build()
        ));

        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable unitKeywordTable : unitKeywordTableList) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpUnitKeywordTable error");
        }
    }

    private void dumpUnitItTable(String filename) {
        List<AdUnitIt> unitItList = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitItList))
            return;

        ArrayList<AdUnitItTable> unitItTableList = Lists.newArrayList();
        unitItList.forEach(item -> unitItTableList.add(
                AdUnitItTable.builder()
                        .unitId(item.getUnitId())
                        .itTag(item.getItTag())
                        .build()
        ));

        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable unitItTable : unitItTableList) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpUnitItTable error");
        }
    }

    private void dumpUnitDistrictTable(String filename) {
        List<AdUnitDistrict> unitDistrictList = districtRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistrictList))
            return;

        ArrayList<AdUnitDistrictTable> unitDistrictTableList = Lists.newArrayList();
        unitDistrictList.forEach(item -> unitDistrictTableList.add(
                AdUnitDistrictTable.builder()
                        .unitId(item.getUnitId())
                        .city(item.getCity())
                        .province(item.getProvince())
                        .build()
        ));
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable unitDistrictTable : unitDistrictTableList) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpUnitDistrictTable error");
        }
    }

}
