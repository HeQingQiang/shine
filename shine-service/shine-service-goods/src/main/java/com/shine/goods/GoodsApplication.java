package com.shine.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/***********
 *@Author: Shine
 *@Description:
 *@Data: Created in 2019/12/25 20:14
 *@Modified By:
 *****/
@SpringBootApplication
@EnableEurekaClient//开起Eureka客户端
@MapperScan(basePackages = {"com.shine.dao"})//开起通用Mapper的包扫描
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

}
