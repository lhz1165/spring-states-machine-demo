package com.lhz.omoa.config.state;

import com.lhz.omoa.enums.Events;
import com.lhz.omoa.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * 一个状态变成多个
 * @author laihz
 * @date 2023/11/27 13:45
 */
@EnableStateMachine
@Configuration
public class ForkStateConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.SI)
                .fork(States.S2)
                .and()
                .withStates()
                    .parent(States.S2)
                    .initial(States.S2I)
                    .state(States.S21)
                    .state(States.S22)
                    .end(States.S2F)
                .and()
                .withStates()
                    .parent(States.S2)
                    .initial(States.S3I)
                    .state(States.S31)
                    .state(States.S32)
                    .end(States.S3F)
                .end(States.SF);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions.withExternal()
                .source(States.SI).target(States.S2).event(Events.E1)
                .and()
                .withFork()
                    .source(States.S2)
                    .target(States.S2I)
                    .target(States.S3I);
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
        };
    }
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

}