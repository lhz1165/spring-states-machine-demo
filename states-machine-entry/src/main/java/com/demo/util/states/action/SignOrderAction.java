package com.demo.util.states.action;


import com.demo.util.states.RepairStateContext;
import com.demo.util.states.order.OrderBO;

/**
 * @author laihz
 * @date 2023/12/1 10:32
 */
public class SignOrderAction extends AbstractOrderAction<OrderBO> {


    @Override
    public void action(RepairStateContext<OrderBO> context) {
        OrderBO contextParam = context.getContextParam();
        System.out.println("orderParam body" + contextParam.getBody());
    }
}
