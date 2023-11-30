package com.lhz.omoa;


import com.lhz.omoa.config.state.ChoiceStateConfig;
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
@SpringBootTest(classes = {ChoiceStateConfig.class})
//@SpringBootTest(classes = {ForkStateConfig.class})
public class OmoaRepairStateTest {
    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Test
    public void test() {
        stateMachine.start();
        stateMachine.sendEvent(Events.E1);
        System.out.println("end");
    }


}
