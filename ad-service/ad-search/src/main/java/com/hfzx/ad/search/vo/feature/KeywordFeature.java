package com.hfzx.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 关键词匹配信息
 * @Date: in 2020/5/11 19:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordFeature {

    private List<String> keywords;

}
