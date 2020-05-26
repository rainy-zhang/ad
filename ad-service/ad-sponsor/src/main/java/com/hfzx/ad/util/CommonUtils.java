package com.hfzx.ad.util;

import com.hfzx.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/22 13:11
 */
public class CommonUtils {

    private static String[] datePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    /**
     * 获取字符串的md5
     * @param value
     * @return
     */
    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    /**
     * 将日期格式的字符串转为java.util.Date
     * @param dateStr
     * @return
     * @throws AdException
     */
    public static Date parseStringToDate(String dateStr) throws AdException {
        try {
            return DateUtils.parseDate(dateStr, datePatterns);
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }
}
