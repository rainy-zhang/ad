package com.hfzx.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @Author: zhangyu
 * @Description: 处理请求前执行的过滤器
 * @Date: in 2020/4/21 13:55
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    /**
     * 定义Filter类型: PreFilter  PostFilter  ErrorFilter
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * Filter执行顺序  值越小顺序越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 表示是否执行该过滤器 true:执行 false:不执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * Filter需要执行的具体逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取当前请求的上下文
        RequestContext context = RequestContext.getCurrentContext();
        context.set("startTime", System.currentTimeMillis());
        return null;
    }
}
