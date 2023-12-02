package com.demo.util.states;

import com.demo.util.states.action.IOrderAction;
import com.demo.util.states.enums.OrderEvent;
import com.demo.util.states.enums.OrderState;
import com.demo.util.states.factory.OrderTransition;
import com.demo.util.states.factory.RepairOrderContextBuilder;
import lombok.Data;

/**
 * @author laihz
 * @date 2023/11/30 14:48
 */
@Data
public class RepairStateContext<T> {

    OrderState source;

    OrderState target;

    OrderTransition<T> transition;

    String orderId;

    /**
     * 参数
     */
    T contextParam;


    RepairStateContext<T> nextContext;

    public RepairStateContext(OrderState source, OrderState target, OrderTransition<T> transition, String orderId, T contextParam) {
        this.source = source;
        this.target = target;
        this.transition = transition;
        this.orderId = orderId;
        this.contextParam = contextParam;
    }

    public RepairStateContext(String orderId, T contextParam) {
        this.contextParam = contextParam;
        this.orderId = orderId;
    }


    public RepairStateContext<T> sendEvent(OrderEvent event) {
        RepairOrderContextBuilder contextBuilder = SpringContextHolder.getBean(RepairOrderContextBuilder.class);
        OrderTransition<T> orderTransition = (OrderTransition<T>) contextBuilder.getTriggerToTransitionMap().get(event);
        if (orderTransition == null) {
            throw new RuntimeException("未配置该事件");
        }
        RepairStateContext<T> context = new RepairStateContext<T>(source, orderTransition.getTarget(), orderTransition, orderId, contextParam);
        IOrderAction<T> action = orderTransition.getAction();
        if (action != null) {
            action.execute(context);
        }
        return context;
    }


    public static <T> RepairStateContext<T> build(String orderId, T orderBO) {
        return new RepairStateContext<T>(orderId, orderBO);
    }


}
