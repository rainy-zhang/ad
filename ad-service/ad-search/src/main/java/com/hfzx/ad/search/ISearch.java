package com.hfzx.ad.search;

import com.hfzx.ad.search.vo.SearchRequest;
import com.hfzx.ad.search.vo.SearchResponse;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/11 19:23
 */
public interface ISearch {

    SearchResponse fetchCreatives(SearchRequest searchRequest);

}
