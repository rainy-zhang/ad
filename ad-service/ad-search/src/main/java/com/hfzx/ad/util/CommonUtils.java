package com.hfzx.ad.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 11:12
 */
@Slf4j
public class CommonUtils {

    /**
     * 获取Map中的数据 如果没有 就创建后返回
     */
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    /**
     * 拼接字符串
     */
    public static String stringConcat(String... args) {
        StringBuilder result = new StringBuilder();
        for (String str : args) {
            result.append(str);
            result.append("-");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    // Mon May 11 00:00:00 CST 2020
    public static Date parseStringDate(String dateStr) {
        try {
            // Locale.US: 美国
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            return DateUtils.addHours(dateFormat.parse(dateStr), -8);
        } catch (ParseException e) {
            log.error("parseStringDate error: {}", dateStr);
            return null;
        }
    }


}
