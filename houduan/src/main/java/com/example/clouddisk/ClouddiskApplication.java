package com.example.clouddisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.clouddisk.dao")
public class ClouddiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClouddiskApplication.class, args);
    }

}
