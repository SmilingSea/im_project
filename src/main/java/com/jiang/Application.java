package com.jiang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
*@author SmilingSea
*2024/3/25
 *
*/

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("项目启动成功...");
    }
}
