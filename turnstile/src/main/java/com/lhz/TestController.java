package com.lhz;

import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author laihz
 * @date 2023/11/27 17:30
 */
@RestController
public class TestController {
    @Resource
    private StateMachine<Application.States, Application.Events> stateMachine;
    @GetMapping("/push")
    public String turn() {
        stateMachine.sendEvent(Application.Events.PUSH);

        return stateMachine.getState().getId().name();
    }
    @GetMapping("/coin")
    public String coin() {
        stateMachine.sendEvent(Application.Events.COIN);
        return stateMachine.getState().getId().name();
    }
}
