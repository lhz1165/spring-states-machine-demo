package com.demo.util.states.action;


import com.demo.util.states.RepairStateContext;
import com.demo.util.states.order.OrderParam;

/**
 * @author laihz
 * @date 2023/12/1 14:22
 */
public class ProcessStartAction extends AbstractOrderAction<OrderParam> {

    @Override
    public void action(RepairStateContext<OrderParam> context) {
        OrderParam contextParam = context.getContextParam();
        System.out.println("orderParam body" + contextParam.getBody());
    }
}
