package com.lhz.omoa.config.trigger;

import com.lhz.omoa.enums.Events;
import com.lhz.omoa.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * @author laihz
 * @date 2023/11/27 15:40
 */
@Configuration
@EnableStateMachine
public class TimerTriggerConfig extends StateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .state(States.S1)
                .state(States.S2);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.SI).target(States.S1).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S1).target(States.S2).event(Events.E2)
                .and()

                //sI->s1 循环执行
                .withInternal()
                    .source(States.S1)
                    .action(timerAction())
                    .timer(1000)
                .and()
                .withInternal()
                    .source(States.S2)
                    .action(timerAction())
                    .timer(1000);
    }

    @Bean
    public Action<States, Events> timerAction() {
        return new Action<States, Events>() {
            @Override
            public void execute(StateContext context) {
                System.out.println(context.getSource().getId()+"timer trigger");
            }
        };
    }
    @Bean
    public StateMachineListener<States, Events> listener() {

        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                if (from != null) {
                    System.out.println("State change from "+from.getId()+"  to " + to.getId());

                }else {
                    System.out.println("State change to " + to.getId());
                }
            }

            @Override
            public void stateContext(StateContext<States, Events> stateContext) {

            }
        };

    }
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener())
                .machineId("myMachineId");
    }
}

