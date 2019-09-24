package com.woniu.markingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.woniu.markingsystem.dao")
@SpringBootApplication
public class MarkingsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarkingsystemApplication.class, args);
    }

}
