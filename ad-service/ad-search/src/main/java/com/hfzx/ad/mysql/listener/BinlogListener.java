package com.hfzx.ad.mysql.listener;

import com.hfzx.ad.mysql.dto.BinlogRowData;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/3 12:01
 */
public interface BinlogListener {

    /**
     * 用来注册不同的监听器
     */
    void register();

    /**
     * 根据BinlogRowData实现增量数据
     * @param eventData
     */
    void onEvent(BinlogRowData eventData);

}
