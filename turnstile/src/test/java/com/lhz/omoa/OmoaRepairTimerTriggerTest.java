package com.lhz.omoa;

import com.lhz.omoa.config.trigger.TimerTriggerConfig;
import com.lhz.omoa.enums.Events;
import com.lhz.omoa.enums.States;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 定时器触发
 * @author laihz
 * @date 2023/11/25 10:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TimerTriggerConfig.class})
public class OmoaRepairTimerTriggerTest {
    @Autowired
    private StateMachine<States, Events> stateMachine;
    @Test
    public void test() throws InterruptedException {
        stateMachine.sendEvent(Events.E1);
        Thread.sleep(10000);
        stateMachine.sendEvent(Events.E2);
        Thread.sleep(10000);
        System.out.println("end");

    }
}
