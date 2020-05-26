package com.hfzx.ad.index.adunit;

import com.hfzx.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @Author: zhangyu
 * @Description: 推广单元索引实体类
 * @Date: in 2020/4/25 10:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Long planId;
    private Integer unitStatus;
    private Integer positionType;
    private AdPlanObject adPlanObject;

    public void update(AdUnitObject newUnitObject) {
        BeanUtils.copyProperties(newUnitObject, this);
    }

    private static boolean isKaiPing(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.KAIPING) > 0;
    }
    private static boolean isTiePian(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN) > 0;
    }
    private static boolean isTiePianMiddle(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE) > 0;
    }
    private static boolean isTiePianPause(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE) > 0;
    }
    private static boolean isTiePianPost(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_POST) > 0;
    }

    /**
     * 判断请求的流量类型是否合法
     * @param adSlotType
     * @param positionType
     * @return
     */
    public static boolean isAdSlotTypeOK(int adSlotType, int positionType) {
        switch (adSlotType) {
            case AdUnitConstants.POSITION_TYPE.KAIPING:
                return isKaiPing(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN:
                return isTiePian(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE:
                return isTiePianMiddle(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE:
                return isTiePianPause(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_POST:
                return isTiePianPost(positionType);
            default:
                return false;
        }
    }

}
