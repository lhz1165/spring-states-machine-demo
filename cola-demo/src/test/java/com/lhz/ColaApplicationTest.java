package com.lhz;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Unit test for simple App.
 */
@SpringBootApplication
public class ColaApplicationTest  {
    public static void main(String[] args) {
        SpringApplication.run(ColaApplicationTest.class, args);
    }
}
