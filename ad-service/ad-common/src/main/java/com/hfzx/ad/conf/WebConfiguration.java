package com.hfzx.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description: 自定义配置类
 * @Date: in 2020/4/21 17:02
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 自定义消息转换器(将Java对象转换为Json对象)
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        // MappingJackson2HttpMessageConverter 该转换器可以将Java对象转换成一个Json对象
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
