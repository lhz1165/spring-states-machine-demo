package com.lhz.omoa.config;

import com.lhz.omoa.enums.Events;
import com.lhz.omoa.enums.States;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.TransitionConflictPolicy;

import java.util.EnumSet;

/**
 * 初始化配置
 * @author laihz
 * @date 2023/11/27 10:24
 */
@Configuration
@EnableStateMachine
public class Config0Enums   extends EnumStateMachineConfigurerAdapter<States, Events> {

    /**
     * 管理states
     * @param states the {@link StateMachineStateConfigurer}
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                //初始的特殊状态
                .initial(States.SI)
                //结束的特殊状态
                .end(States.SF)
                //常规所有状态
                .states(EnumSet.allOf(States.class));

    }

    /**
     * SI->S1->S2->SF
     * transitions定义
     * 类型 external，internal，local
     * @param transitions the {@link StateMachineTransitionConfigurer}
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
                //transitions配置单个事件触发的action
                .withExternal().source(States.SI).target(States.S1).event(Events.E1).action(action())
                .and()
                .withExternal().source(States.S1).target(States.S2).event(Events.E2).action(action2())
                .and()
                .withExternal().source(States.S2).target(States.SF).event(Events.E3).action(action2());

    }


    /**
     * 定义action
     * 两种方式配置
     * 1. 在transitions配置单个事件发生时候action
     * 2. 在states配置进入状态和退出状态对应的action
     * 在transition发送event给state时执行action
     * @return
     */
    @Bean
    public Action<States, Events> action() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                // do something
                System.out.println("");
            }
        };
    }
    @Bean
    public Action<States, Events> action2() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                // do something
                System.out.println("");
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
        };

    }


    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                //.autoStartup(true)
                .listener(listener())
                .machineId("myMachineId")
                .beanFactory(new StaticListableBeanFactory())
                .taskExecutor(new SyncTaskExecutor())
                .taskScheduler(new ConcurrentTaskScheduler())
                .transitionConflictPolicy(TransitionConflictPolicy.CHILD);
    }

}
