package com.lhz.omoa;

import com.lyt.ims.omoa.config.state.EntryAndExitStateConfig;
import com.lhz.omoa.enums.Events;
import com.lhz.omoa.enums.States;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EntryAndExitStateConfig.class})
public class OmoaRepairEntryAndExitStateTest {
    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Test
    public void test() {
        stateMachine.sendEvent(Events.E2);
        stateMachine.sendEvent(Events.E3);
    }
}
