package com.demo.util.states.factory;

import com.demo.util.states.action.IOrderAction;
import com.demo.util.states.enums.OrderEvent;
import com.demo.util.states.enums.OrderState;
import lombok.Data;

/**
 * @author laihz
 * @date 2023/11/30 14:30
 */
@Data
public class OrderTransition<T> {
    OrderState source;
    OrderState target;
    OrderEvent event;
    IOrderAction<T> action;

    RepairOrderContextBuilder builder;

    public OrderTransition() {
    }

    public OrderTransition(OrderState source, OrderState target, OrderEvent event) {
        this.source = source;
        this.target = target;
        this.event = event;
    }

    public OrderTransition(OrderState source, OrderState target, OrderEvent event, IOrderAction<T> action) {
        this.source = source;
        this.target = target;
        this.event = event;
        this.action = action;
    }

    public OrderTransition<T> source(OrderState source) {
        setSource(source);
        return this;
    }

    public OrderTransition<T> target(OrderState target) {
        setTarget(target);
        return this;
    }

    public OrderTransition<T> event(OrderEvent event) {
        setEvent(event);
        return this;
    }

    public RepairOrderContextBuilder and() {
        if (builder == null) {
            throw new IllegalStateException("Builder cannot be null");
        }
        return getBuilder();
    }

}
