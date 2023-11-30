package com.lhz.omoa;

//import com.lhz.omoa.config.guardGuardConfig;
import com.lhz.omoa.config.guard.GuardConfig;
import com.lhz.omoa.enums.Events;
import com.lhz.omoa.enums.States;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author laihz
 * @date 2023/11/25 10:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GuardConfig.class})
public class OmoaRepairGuardTest {
    @Autowired
    private StateMachine<States, Events> stateMachine;
    @Test
    public void test() {
        stateMachine.sendEvent(Events.E1);
        stateMachine.sendEvent(Events.E2);
        System.out.println("end");
    }
}
