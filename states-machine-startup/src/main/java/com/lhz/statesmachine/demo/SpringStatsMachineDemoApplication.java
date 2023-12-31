package com.lhz.statesmachine.demo;

import com.lhz.statesmachine.demo.enums.Events;
import com.lhz.statesmachine.demo.enums.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * history
 */
@SpringBootApplication
public class SpringStatsMachineDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStatsMachineDemoApplication.class, args);
    }


}
