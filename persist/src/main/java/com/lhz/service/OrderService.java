package com.lhz.service;

import com.lhz.enums.OrderEvents;
import com.lhz.enums.OrderStates;
import com.lhz.mapper.OrderMapper;
import com.lhz.pojo.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
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
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private StateMachineFactory<OrderStates, OrderEvents> factory;
    public void create(){
        orderMapper.insert(new Order(OrderStates.SUBMITTED.name()));
    }
    private static  final String ORDER_ID_HEADER="orderId";

    public StateMachine<OrderStates, OrderEvents> pay(Long orderId,String paymentMsg) {
        StateMachine<OrderStates, OrderEvents> machine = this.build(orderId);
        Message<OrderEvents> msg = MessageBuilder.withPayload(OrderEvents.PAY)
                .setHeader(ORDER_ID_HEADER, orderId)
                .setHeader("payMsg", "PAY ok")
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

    public StateMachine<OrderStates, OrderEvents> build(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        String orderIdKey = Long.toString(order.getId());
        StateMachine<OrderStates, OrderEvents> stateMachine = factory.getStateMachine(orderIdKey);
        stateMachine.stop();

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(new StateMachineFunction<StateMachineAccess<OrderStates, OrderEvents>>() {
                    @Override
                    public void apply(StateMachineAccess<OrderStates, OrderEvents> sma) {
                        OrderStates state = order.getState();
                        //再状态改变之前写入数据库
                        sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<OrderStates, OrderEvents>(){
                            @Override
                            public void preStateChange(State<OrderStates, OrderEvents> state, Message<OrderEvents> message, Transition<OrderStates, OrderEvents> transition, StateMachine<OrderStates, OrderEvents> stateMachine, StateMachine<OrderStates, OrderEvents> rootStateMachine) {
                                Long id = (Long)message.getHeaders().getOrDefault(ORDER_ID_HEADER, -1L);
                                Order newOrder = new Order(id, state.getId().name());
                                orderMapper.updateById(newOrder);
                            }
                        });
                        sma.resetStateMachine(new DefaultStateMachineContext<OrderStates, OrderEvents>(state,null,null,null));
                    }
                });
        stateMachine.start();
        return stateMachine;
    }

}
