package com.lhz.service;

import com.lhz.config.StateMachineInterceptorConfig;
import com.lhz.enums.OrderEvents;
import com.lhz.enums.OrderStates;
import com.lhz.mapper.OrderMapper;
import com.lhz.pojo.Order;
import com.mysql.cj.log.Log;
import jdk.nashorn.internal.ir.IfNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author laihz
 * @date 2023/11/28 9:32
 */
@Log4j2
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private StateMachineFactory<OrderStates, OrderEvents> factory;

    @Resource
    private StateMachineInterceptorConfig interceptorConfig;

    public Order get(Long id) {
        return orderMapper.selectById(id);
    }

    public Order create(){
        Order order = new Order(OrderStates.SUBMITTED.name());
        orderMapper.insert(order);
        return order;
    }


    public static  final String ORDER_ID_HEADER="orderId";

    public StateMachine<OrderStates, OrderEvents> pay(Long orderId) {

        StateMachine<OrderStates, OrderEvents> machine = this.build(orderId);

        //通过header设置想要传递的参数
        Message<OrderEvents> msg = MessageBuilder
                .withPayload(OrderEvents.PAY)
                .setHeader(ORDER_ID_HEADER, orderId)
                .build();

        machine.sendEvent(msg);
        return machine;
    }

    public StateMachine<OrderStates, OrderEvents> fulfill(Long orderId) {
        StateMachine<OrderStates, OrderEvents> machine = this.build(orderId);

        Message<OrderEvents> msg = MessageBuilder.withPayload(OrderEvents.FULFILL)
                .setHeader(ORDER_ID_HEADER, orderId)
                .setHeader("payMsg", "FULFILL ok")
                .build();
        machine.sendEvent(msg);

        return machine;
    }

    public StateMachine<OrderStates, OrderEvents> cancel(Long orderId) {
        StateMachine<OrderStates, OrderEvents> machine = this.build(orderId);

        Message<OrderEvents> msg = MessageBuilder.withPayload(OrderEvents.CANCEL)
                .setHeader(ORDER_ID_HEADER, orderId)
                .setHeader("cancel", "cancel")
                .build();
        machine.sendEvent(msg);

        return machine;
    }

    /**
    * 根据订单id生成一个StateMachine
    * @author laihz
    * @date 2023/11/29 10:36
    * @param orderId
    * @return org.springframework.statemachine.StateMachine<com.lhz.enums.OrderStates,com.lhz.enums.OrderEvents>
    */
    public StateMachine<OrderStates, OrderEvents> build(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        String orderIdKey = Long.toString(order.getId());
        StateMachine<OrderStates, OrderEvents> stateMachine = factory.getStateMachine(orderIdKey);

        //通过StateMachineAccessor访问到StateMachine内部
        stateMachine.getStateMachineAccessor()
                //所有Regions
                .doWithAllRegions(sma -> {
                    OrderStates state = order.getState();
                    //配置拦截器
                    sma.addStateMachineInterceptor(interceptorConfig);
                     //重置状态机
                    sma.resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null, null, stateMachine.getId()));
                    }
                );
        stateMachine.start();
        return stateMachine;
    }

}
