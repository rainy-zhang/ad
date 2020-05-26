package com.hfzx.ad.index;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.dump.DumpConstant;
import com.hfzx.ad.dump.table.*;
import com.hfzx.ad.handler.AdLevelDataHandler;
import com.hfzx.ad.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhangyu
 * @Description: 加载文件中的数据
 * @Date: in 2020/5/1 20:52
 */
@Component
//该注解用于声明当前bean依赖于另外一个bean。所依赖的bean会被容器确保在当前bean实例化之前被实例化。一般用在一个bean没有通过属性或者构造函数参数显式依赖另外一个bean，但实际上会使用到那个bean或者那个bean产生的某些结果的情况。
@DependsOn(value = "dataTable")
public class IndexFileLoader {

    @PostConstruct
    public void init() {
        List<String> planStrings = loadDumpData(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_PLAN));
        planStrings.forEach(item -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(item, AdPlanTable.class),
                OpType.ADD
        ));

        List<String> creativeStrings = loadDumpData(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_CREATIVE));
        creativeStrings.forEach(item -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(item, AdCreativeTable.class),
                OpType.ADD
        ));

        List<String> unitStrings = loadDumpData(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT));
        unitStrings.forEach(item -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(item, AdUnitTable.class),
                OpType.ADD
        ));

        List<String> creativeUnitStrings = loadDumpData(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_CREATIVE_UNIT));
        creativeUnitStrings.forEach(item -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(item, AdCreativeUnitTable.class),
                OpType.ADD
        ));

        List<String> unitDistrictStrings = loadDumpData(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_DISTRICT));
        unitDistrictStrings.forEach(item -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(item, AdUnitDistrictTable.class),
                OpType.ADD
        ));

        List<String> unitItStrings = loadDumpData(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_IT));
        unitItStrings.forEach(item -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(item, AdUnitItTable.class),
                OpType.ADD
        ));

        List<String> unitKeywordStrings = loadDumpData(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_KEYWORD));
        unitKeywordStrings.forEach(item -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(item, AdUnitKeywordTable.class),
                OpType.ADD
        ));
    }

    private List<String> loadDumpData(String filename) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
