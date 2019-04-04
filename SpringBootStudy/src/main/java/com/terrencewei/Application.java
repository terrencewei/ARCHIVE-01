package com.terrencewei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.terrencewei.mybatis.mapper")
public class Application {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
        System.out.println("===================================");
        System.out.println();
        System.out.println("      SpringBoot running");
        System.out.println();
        System.out.println("===================================");
    }

}