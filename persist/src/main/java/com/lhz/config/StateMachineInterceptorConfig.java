package com.lhz.config;

import com.lhz.enums.OrderEvents;
import com.lhz.enums.OrderStates;
import com.lhz.mapper.OrderMapper;
import com.lhz.pojo.Order;
import com.lhz.service.OrderService;
import com.mysql.cj.log.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author laihz
 * @date 2023/11/29 14:40
 */
@Log4j2
@Component
public class StateMachineInterceptorConfig extends StateMachineInterceptorAdapter<OrderStates, OrderEvents> {
    @Resource
    private OrderMapper orderMapper;
    @Override
    public void postStateChange(State<OrderStates, OrderEvents> state, Message<OrderEvents> message, Transition<OrderStates, OrderEvents> transition, StateMachine<OrderStates, OrderEvents> stateMachine, StateMachine<OrderStates, OrderEvents> rootStateMachine) {
        try {
            Long id = (Long)message.getHeaders().getOrDefault(OrderService.ORDER_ID_HEADER, -1L);
            Order newOrder = new Order(id, state.getId().name());
            orderMapper.updateById(newOrder);
        } catch (Exception e) {
            stateMachine.setStateMachineError(e);
        }
    }

    @Override
    public Message<OrderEvents> preEvent(Message<OrderEvents> message, StateMachine<OrderStates, OrderEvents> stateMachine) {
        return super.preEvent(message, stateMachine);
    }

    @Override
    public void preStateChange(State<OrderStates, OrderEvents> state, Message<OrderEvents> message, Transition<OrderStates, OrderEvents> transition, StateMachine<OrderStates, OrderEvents> stateMachine, StateMachine<OrderStates, OrderEvents> rootStateMachine) {
        super.preStateChange(state, message, transition, stateMachine, rootStateMachine);
    }
    @Override
    public Exception stateMachineError(StateMachine<OrderStates, OrderEvents> sm, Exception exception) {
        //todo 出现异常
        log.info("3. 拦截器处理异常");
        if(!sm.getExtendedState().getVariables().containsKey("exception")){
            sm.getExtendedState().getVariables().put("exception",exception);
        }
        return exception;
    }

}

