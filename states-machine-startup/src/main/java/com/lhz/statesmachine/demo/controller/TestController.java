package com.lhz.statesmachine.demo.controller;

import com.lhz.statesmachine.demo.enums.Events;
import com.lhz.statesmachine.demo.enums.States;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author laihz
 * @date 2023/11/28 10:28
 */
@RestController
public class TestController {
    @Resource
    private StateMachine<States, Events> stateMachine;

    @GetMapping("/sendEvent")
    public String send(@RequestParam String event) {
        Events events = Events.valueOf(event);
        Message<Events> msg = MessageBuilder
                .withPayload(events)
                .build();
        stateMachine.sendEvent(msg);
        return "Event " + event + " Send";
    }
}
