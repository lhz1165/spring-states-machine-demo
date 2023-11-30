package com.lhz.omoa.example;


import com.lyt.ims.omoa.config.Config0Enums;
import com.lhz.omoa.enums2.Events2;
import com.lhz.omoa.enums2.States2;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author laihz
 * @date 2023/11/28 15:50
 */
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Config0Enums.class})
public class WasherExampleTest {

    @Test
    public void historyStateTest() throws Exception {
        StateMachineBuilder.Builder<States2, Events2> builder = StateMachineBuilder.builder();
        //state
        builder.configureStates()
                .withStates()
                .initial(States2.RUNNING)
                .state(States2.POWEROFF)
                .end(States2.END)
                .and()
                    .withStates()
                    .parent(States2.RUNNING)
                    .initial(States2.WASHING)
                    .state(States2.RINSING)
                    .state(States2.DRYING)
                    .history(States2.HISTORY, StateConfigurer.History.DEEP)
                    .and()
                            .withStates()
                            .parent(States2.DRYING)
                            .initial(States2.IDRYING)
                            .state(States2.INDRYING);
        //transition
        builder.configureTransitions()
                .withExternal().source(States2.WASHING).target(States2.RINSING).event(Events2.RINSE)
                .and()
                .withExternal().source(States2.RINSING).target(States2.DRYING).event(Events2.DRY)
                .and()
                .withExternal().source(States2.RUNNING).target(States2.POWEROFF).event(Events2.CUTPOWER)
                .and()
                .withExternal().source(States2.POWEROFF).target(States2.HISTORY).event(Events2.RESTOREPOWER)
                .and()
                .withExternal().source(States2.IDRYING).target(States2.INDRYING).event(Events2.S1)
                .and()
                .withExternal().source(States2.DRYING).target(States2.END).event(Events2.STOP);

        //Configuration
        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(false)
                .listener(listener());


        StateMachine<States2, Events2> machine = builder.build();
        machine.start();
        machine.sendEvent(Events2.RINSE);
        machine.sendEvent(Events2.DRY);
        machine.sendEvent(Events2.S1);
        machine.sendEvent(Events2.CUTPOWER);
        machine.sendEvent(Events2.RESTOREPOWER);
    }


    public StateMachineListener<States2, Events2> listener() {
        return new StateMachineListenerAdapter<States2, Events2>() {
            @Override
            public void stateChanged(State<States2, Events2> from, State<States2, Events2> to) {
                if (from != null) {
                    System.out.println("State change from " + from.getId() + "  to " + to.getId());

                } else {
                    System.out.println("State change to " + to.getId());
                }
            }
        };
    }
}
