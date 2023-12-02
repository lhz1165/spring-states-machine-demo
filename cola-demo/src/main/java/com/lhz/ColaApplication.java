package com.lhz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 * @author 21907
 */
@SpringBootApplication
@MapperScan("com.lhz.mapper")
public class ColaApplication {
    public static void main(String[] args) {
        new SpringApplication(ColaApplication.class).run();
    }

}
