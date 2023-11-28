package com.lhz;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author 21907
 */
@MapperScan("com.lhz.mapper")
@SpringBootApplication
public class SpringStatsMachinePersistApplication implements CommandLineRunner {
    @Resource
    DataSource dataSource;
    public static void main(String[] args) {
        SpringApplication.run(SpringStatsMachinePersistApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println(dataSource);
    }
}
