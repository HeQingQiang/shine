package com.shine.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/***********
 *@Author: Shine
 *@Description:
 *@Data: Created in 2019/12/27 23:25
 *@Modified By:
 *****/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//排除数据库加载
@EnableEurekaClient
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
