package com.lhz;

/**
 * @author laihz
 * @date 2023/11/27 17:25
 */
import java.util.EnumSet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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


@SpringBootApplication
public class Application  {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //tag::snippetA[]
    @Configuration
    @EnableStateMachine
    static class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

        @Override
        public void configure(StateMachineStateConfigurer<States, Events> states)
                throws Exception {
            states
                    .withStates()
                    .initial(States.LOCKED)
                    .states(EnumSet.allOf(States.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
                throws Exception {
            transitions
                    .withExternal()
                        .source(States.LOCKED)
                        .target(States.UNLOCKED)
                        .event(Events.COIN)
                    .and()
                    .withExternal()
                        .source(States.UNLOCKED)
                        .target(States.LOCKED)
                        .event(Events.PUSH);
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

    public enum States {
        LOCKED, UNLOCKED
    }

    public enum Events {
        COIN, PUSH
    }




}
