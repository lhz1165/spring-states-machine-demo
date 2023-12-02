package com.lhz.config;

import com.lhz.enums.OrderEvents;
import com.lhz.enums.OrderStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * Hello world!
 *
 */
public class PayAction implements Action<OrderStates, OrderEvents> {
    @Override
    public void execute(StateContext<OrderStates, OrderEvents> context) {

    }
}
