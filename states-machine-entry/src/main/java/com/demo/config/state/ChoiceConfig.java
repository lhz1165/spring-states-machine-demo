package com.demo.config.state;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

/**
 * @author laihz
 * @date 2023/11/27 10:53
 */
@Configuration
@EnableStateMachine
public class ChoiceConfig extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {
        states
                .withStates()
                .initial("S1")
                .state("S2")
                .state("S3")
                .and()
                .withStates()
                .parent("S2")
                .initial("S21")
                .entry("S2ENTRY")
                .exit("S2EXIT")
                .state("S22");
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source("S1").target("S2").event("E1")
                .and()
                .withExternal()
                .source("S1").target("S2ENTRY").event("ENTRY")
                .and()
                .withExternal()
                .source("S22").target("S2EXIT").event("EXIT")
                .and()
                .withEntry()
                .source("S2ENTRY").target("S22")
                .and()
                .withExit()
                .source("S2EXIT").target("S3");
    }

    @Bean
    public StateMachineListener<String, String> listener() {
        return new StateMachineListenerAdapter<String, String>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                if (from != null) {
                    System.out.println("State change from " + from.getId() + "  to " + to.getId());

                } else {
                    System.out.println("State change to " + to.getId());
                }
            }
        };
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(false)
                .listener(listener());
    }
}