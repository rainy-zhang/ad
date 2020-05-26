package com.hfzx.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/1 19:19
 */
public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OpType to(EventType eventType) {
        switch (eventType) {
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
