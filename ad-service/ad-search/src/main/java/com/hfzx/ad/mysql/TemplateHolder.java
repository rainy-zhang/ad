package com.hfzx.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.mysql.constant.OpType;
import com.hfzx.ad.mysql.dto.BinlogTemplate;
import com.hfzx.ad.mysql.dto.ParseTemplate;
import com.hfzx.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/3 11:28
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;
    private final JdbcTemplate jdbcTemplate;
    private String SQL_SCHEMA = "select table_schema, table_name, column_name, ordinal_position from information_schema.columns where table_schema = ? and table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        loadJson("binlog_template.json");
    }

    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    /**
     * 加载binlog模板文件
     * @param path 文件路径
     */
    private void loadJson(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);

        try {
            BinlogTemplate binlogTemplate = JSON.parseObject(inputStream, Charset.defaultCharset(), BinlogTemplate.class);
            this.template = template.parse(binlogTemplate);
            loadMeta();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("not found binlog_template.json");
        }
    }

    /**
     * 加载列名
     */
    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();

            List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{template.getDatabase(), table.getTableName()}, (rs, i) -> {
                Integer position = rs.getInt("ORDINAL_POSITION");
                String columnName = rs.getString("COLUMN_NAME");

                if ((null != insertFields && insertFields.contains(columnName))
                || (null != updateFields && updateFields.contains(columnName))
                || (null != deleteFields && deleteFields.contains(columnName))) {
                    table.getPosMap().put(position, columnName);
                }
                return null;
            });

        }
    }

}
