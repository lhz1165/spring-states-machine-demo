package com.lhz.omoa.config.state;

import com.lhz.omoa.enums.Events;
import com.lhz.omoa.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * entry和exit是一个入口，会自动变成入口之后的状态
 *
 * @author laihz
 * @date 2023/11/27 14:21
 */
@EnableStateMachine
@Configuration
public class EntryAndExitStateConfig extends StateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
               .withStates()
               .initial(States.SI)
                .state(States.S2)
                .and()
                .withStates()
                    .parent(States.S2)
                    .initial(States.S2I)
                    .entry(States.S2ENTRY)
                    .state(States.S21)
                    .exit(States.S2EXIT)
                .and()
                .withStates()
                .state(States.S3);
    }

    /**
     * SI->(事件E2进入S2ENTRY）->（S2）S21->(事件E3进入S2EXIT)-> S3
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(States.SI).target(States.S2ENTRY).event(Events.E2)
                .and()
                .withExternal()
                .source(States.S21).target(States.S2EXIT).event(Events.E3)
                .and()
                .withEntry()
                .source(States.S2ENTRY).target(States.S21)
                .and()
                .withExit()
                .source(States.S2EXIT).target(States.S3);
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
