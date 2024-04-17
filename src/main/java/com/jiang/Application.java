package com.jiang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
*@author SmilingSea
*2024/3/25
 *
*/

@Slf4j
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })
@ServletComponentScan
@EnableTransactionManagement
public class Application implements ServletContextInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("项目启动成功...");


    }


    /**
     * 设置websocket消息内容长度
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","10240");
    }
}
