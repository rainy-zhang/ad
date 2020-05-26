package com.hfzx.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/20 13:52
 */

@EnableEurekaClient
@SpringBootApplication
public class DataDumpApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataDumpApplication.class, args);
    }

}
