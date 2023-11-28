package com.lhz.statesmachine.demo.config.state;

import com.lhz.statesmachine.demo.enums.Events;
import com.lhz.statesmachine.demo.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.StateConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * @author laihz
 * @date 2023/11/28 10:22
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.RUNNING)
                .state(States.POWEROFF)
                .end(States.END)
                .and()
                .withStates()
                    .parent(States.RUNNING)
                    .initial(States.WASHING)
                    .state(States.RINSING)
                    .state(States.DRYING)
                    .history(States.HISTORY, StateConfigurer.History.DEEP);
    }
//end::snippetAA[]

    //tag::snippetAB[]
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                .withExternal().source(States.WASHING).target(States.RINSING).event(Events.RINSE)
                .and()
                .withExternal().source(States.RINSING).target(States.DRYING).event(Events.DRY)
                .and()
                .withExternal().source(States.RUNNING).target(States.POWEROFF).event(Events.CUTPOWER)
                .and()
                .withExternal().source(States.POWEROFF).target(States.HISTORY).event(Events.RESTOREPOWER)
                .and()
                .withExternal().source(States.RUNNING).target(States.END).event(Events.STOP);
    }
    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                if (from != null) {
                    System.out.println("State change from " + from.getId() + "  to " + to.getId());

                } else {
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
