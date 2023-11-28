package com.lhz.config;

import com.lhz.enums.OrderEvents;
import com.lhz.enums.OrderStates;
import com.lhz.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Log4j2
public class Runner implements ApplicationRunner {
    @Resource
    OrderService orderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        orderService.create();

       orderService.pay(3L,"pay test");

       orderService.fulfill(3L);


    }
}
