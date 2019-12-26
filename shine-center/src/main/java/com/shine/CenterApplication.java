package com.shine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/***********
    *@Author: Shine
    *@Description:
    *@Data: Created in 2019/12/24 22:22
    *@Modified By:
 *****/
@SpringBootApplication
@EnableEurekaServer
public class CenterApplication {

    /**
     *@Author: Shine
     *@Description:加载启动类。已启动类为当前springboot的配置标准
    **/
    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class,args);
    }

}
