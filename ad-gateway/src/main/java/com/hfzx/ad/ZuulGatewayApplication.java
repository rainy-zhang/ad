package com.hfzx.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/21 10:39
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }

}
