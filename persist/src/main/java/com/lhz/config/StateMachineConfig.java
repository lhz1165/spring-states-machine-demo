package com.lhz.config;

import com.lhz.enums.OrderEvents;
import com.lhz.enums.OrderStates;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 * @author laihz
 * @date 2023/11/27 19:33
 */
@Log4j2
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderStates, OrderEvents> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        config.withConfiguration()
                .autoStartup(false)
                .listener(customListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {

        states.withStates()
                .initial(OrderStates.SUBMITTED)
                //进入状态事件
                .stateEntry(OrderStates.SUBMITTED, new Action<OrderStates, OrderEvents>() {
                    @Override
                    public void execute(StateContext<OrderStates, OrderEvents> stateContext) {
                        Long orderId = (Long) stateContext.getExtendedState().getVariables().getOrDefault("orderId", -1);
                        log.info("orderId is {}", orderId);
                        log.info("entry submitted state!");
                    }
                })
                .state(OrderStates.PAID)
                .state(OrderStates.FULFILLED)
                .end(OrderStates.CANCELLED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStates.SUBMITTED).target(OrderStates.PAID).event(OrderEvents.PAY).action(new Action<OrderStates, OrderEvents>() {
                    @Override
                    public void execute(StateContext<OrderStates, OrderEvents> context) {
                        Long orderId = (Long) context.getExtendedState().getVariables().getOrDefault("orderId", -1);
                        log.info("orderId is {}", orderId);
                        log.info("Transition from SUBMITTED to PAID state!");
                    }
                })
                .and()
                .withExternal().source(OrderStates.SUBMITTED).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrderStates.PAID).target(OrderStates.FULFILLED).event(OrderEvents.FULFILL)
                .and()
                .withExternal().source(OrderStates.PAID).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrderStates.FULFILLED).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL);
    }


    private StateMachineListenerAdapter<OrderStates, OrderEvents> customListener() {
        return new StateMachineListenerAdapter<OrderStates, OrderEvents>() {
            @Override
            public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
                if (from != null) {
                    log.info("state changed(from {} to {})", from, to);
                } else {
                    log.info("state changed(to {})", to);
                }
            }

            /**
             * 没有配置状态不被允许改变
             * @param event the event
             */
            @Override
            public void eventNotAccepted(Message<OrderEvents> event) {
                Object orderId = event.getHeaders().get("orderId");
                OrderEvents orderEvent = event.getPayload();
                log.error("状态改变失败 orderId {}，orderEvent {}", orderId, orderEvent);
               // stateMachineError(stateMachine,new RuntimeException("状态不允许改变"));
            }


            @Override
            public void stateContext(StateContext<OrderStates, OrderEvents> stateContext) {
                //出现不存在的状态转移
                if (stateContext.getStage().equals(StateContext.Stage.EVENT_NOT_ACCEPTED)) {
                    OrderEvents to = stateContext.getMessage().getPayload();
                    OrderStates source = stateContext.getSource().getId();
                    //设置异常 在拦截器做异常处理
                    stateContext.getStateMachine().setStateMachineError(new RuntimeException("状态不允许改变from "+ source+" to "+to));
                }

            }
        };
    }
}
