package com.lhz.config;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.lhz.enums.OrderEvent;
import com.lhz.enums.OrderStatusEnum;
import com.lhz.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author laihz
 * @date 2023/12/2 9:49
 */
@Component
public class StateMachineBuilderConfig {
    @Autowired
    UserCloseAction userCloseAction;

    @Bean("orderOperaMachine")
    public StateMachine<OrderStatusEnum, OrderEvent, Order> orderOperaMachine() {
        String ORDER_OPERA = "order_opera";
        StateMachineBuilder<OrderStatusEnum, OrderEvent, Order> builder = StateMachineBuilderFactory.create();
        //订单从初始化状态-待发货-状态-转到-关闭订单状态--用户关闭
        builder.externalTransitions()
                .fromAmong(OrderStatusEnum.INIT, OrderStatusEnum.WAITING_FOR_DELIVERY)
                .to(OrderStatusEnum.CLOSE)
                .on(OrderEvent.USER_CLOSE)
                .when(checkCondition())
                .perform(userCloseAction);
        //订单从-初始化状态-已发货-待发货--转到-关闭订单状态--后台操作人员关闭
        builder.externalTransitions()
                .fromAmong(OrderStatusEnum.INIT, OrderStatusEnum.HAVE_BEEN_DELIVERY, OrderStatusEnum.WAITING_FOR_DELIVERY)
                .to(OrderStatusEnum.CLOSE)
                .on(OrderEvent.ADMIN_CLOSE)
                .when(checkCondition())
                .perform(doAction());
        //订单从等待发货状态-转为-订单关闭状态-超时关闭
        builder.externalTransition()
                .from(OrderStatusEnum.WAITING_FOR_DELIVERY)
                .to(OrderStatusEnum.CLOSE)
                .on(OrderEvent.OVERTIME_CLOSE)
                .when(checkCondition())
                .perform(doAction());
        //订单从待发货状态--转为-订单关闭状态-上级审批不通过关闭
        builder.externalTransition()
                .from(OrderStatusEnum.WAITING_FOR_DELIVERY)
                .to(OrderStatusEnum.CLOSE)
                .on(OrderEvent.CHECK_ERROR_CLOSE)
                .when(checkCondition())
                .perform(doAction());
        //订单从初始化状态--转为待发货状态--用户支付完毕动
        builder.externalTransition()
                .from(OrderStatusEnum.INIT)
                .to(OrderStatusEnum.WAITING_FOR_DELIVERY)
                .on(OrderEvent.USER_PAY)
                .when(checkCondition())
                .perform(doAction());

        StateMachine<OrderStatusEnum, OrderEvent, Order> orderOperaMachine = builder.build(ORDER_OPERA);

        //打印uml图
        String plantUML = orderOperaMachine.generatePlantUML();
        System.out.println(plantUML);
        return orderOperaMachine;
    }

    private Condition<Order> checkCondition() {
        return (ctx) -> {
            return true;
        };
    }

    private Action<OrderStatusEnum, OrderEvent, Order> doAction() {
        return (from, to, event, ctx) -> {
            System.out.println(ctx.getOrderName() + " 正在操作 " + ctx.getOrderId() + " from:" + from + " to:" + to + " on:" + event);
            throw new RuntimeException("error");
        };
    }

}