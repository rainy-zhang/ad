package com.hfzx.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhangyu
 * @Description: 请求处理后执行的过滤器
 * @Date: in 2020/4/21 14:13
 */
@Slf4j
@Component
public class AccessLogFilter extends ZuulFilter {

    /**
     * 定义Filter类型: PreFilter  PostFilter  ErrorFilter
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    /**
     * Filter执行顺序  值越小顺序越高
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
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
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        Long startTime = (Long) context.get("startTime");
        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;

        log.info("uri: {}, duration: {}ms", uri, duration/1000);
        return null;
    }
}
