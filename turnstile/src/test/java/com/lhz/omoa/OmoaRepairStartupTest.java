package com.lhz.omoa;

import com.lhz.omoa.config.Config0Enums;
import com.lhz.omoa.enums.Events;
import com.lhz.omoa.enums.States;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author laihz
 * @date 2023/11/25 10:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Config0Enums.class})
public class OmoaRepairStartupTest {
    @Autowired
    private StateMachine<States, Events> stateMachine;

    /**
     * 通过configure配置stateMachine
     *
     * @param
     * @return void
     * @author laihz
     * @date 2023/11/28 13:56
     */
    @Test
    public void sendEvenTest() {
        stateMachine.start();
        Message<Events> e1 = MessageBuilder
                .withPayload(Events.E1)
                .setHeader("foo", "bar")
                .build();
        stateMachine.sendEvent(e1);

        Message<Events> e2 = MessageBuilder
                .withPayload(Events.E2)
                .setHeader("foo", "bar")
                .build();
        stateMachine.sendEvent(e2);
        stateMachine.sendEvent(Events.E3);
    }

    /**
     * 通过StateMachineBuilder配置stateMachine
     *
     * @param
     * @return void
     * @author laihz
     * @date 2023/11/28 13:56
     */
    @Test
    public void stateMachineBuildTest() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        StateConfigurer<String, String> configurer = builder.configureStates()
                .withStates()
                .initial("S1")
                .end("SF")
                .states(new HashSet<String>(Arrays.asList("S2", "S3", "S4")));

        builder.configureTransitions()
                .withExternal()
                .source("S1").target("S2").event("E1")
                .and()
                .withExternal()
                .source("S2").target("S3").event("E2");

        builder.configureConfiguration().withConfiguration()
                .autoStartup(false)
                .listener(new StateMachineListenerAdapter<String, String>() {
                    @Override
                    public void stateChanged(State<String, String> from, State<String, String> to) {
                        if (from != null) {
                            System.out.println("State change from " + from.getId() + "  to " + to.getId());

                        } else {
                            System.out.println("State change to " + to.getId());
                        }
                    }
                });
        StateMachine<String, String> machine = builder.build();


        machine.start();
        machine.sendEvent("E1");
        machine.sendEvent("E2");
    }

    /**
     * 多层嵌套包含子状态的
     *
     * @param
     * @return void
     * @author laihz
     * @date 2023/11/28 14:23
     */
    @Test
    public void subStateMachineBuildTest() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial("S1")
                .state("S2")
                .state("S3")
                .end("SF")
                    .and()
                    .withStates()
                    .parent("S2")
                    .initial("S2I")
                    .state("S21")
                    .and()
                        .withStates()
                        .parent("S21")
                        .initial("S21I")
                        .state("S211");

        builder.configureTransitions()
                .withExternal().source("S1").target("S2").event("E1")
                .and()
                .withExternal().source("S2I").target("S21").event("E2")
                .and()
                .withExternal().source("S21I").target("S211").event("E3")
                .and()
                .withExternal().source("S2").target("S3").event("E4");


        builder.configureConfiguration().withConfiguration()
                .autoStartup(false)
                .listener(new StateMachineListenerAdapter<String, String>() {
                    @Override
                    public void stateChanged(State<String, String> from, State<String, String> to) {
                        if (from != null) {
                            System.out.println("State change from " + from.getId() + "  to " + to.getId());

                        } else {
                            System.out.println("State change to " + to.getId());
                        }
                    }
                });

        StateMachine<String, String> machine = builder.build();
        machine.start();

        machine.sendEvent("E1");
        machine.sendEvent("E2");
        machine.sendEvent("E4");

    }


}
