package com.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class SpringStatsMachineDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringStatsMachineDemoApplication.class, args);
    }

    @Autowired
    private StateMachine<String, String> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent("ENTRY");
        stateMachine.sendEvent("EXIT");
    }
}
