package com.demo.util.states.action;


import com.demo.util.states.RepairStateContext;
import com.demo.util.states.enums.OrderState;

/**
 * @author laihz
 * @date 2023/12/1 9:44
 */
public abstract class AbstractOrderAction<T> implements IOrderAction<T> {

    void preAction(RepairStateContext<T> context) {
        //todo 查询数据库 比较初始状态是否一致
        OrderState orderSateInDataBase = null;
        if ("123".equals(context.getOrderId())) {
            orderSateInDataBase = OrderState.WAITING_SIGNED;
        }
        if ("456".equals(context.getOrderId())) {
            orderSateInDataBase = OrderState.WAITING_PROCESS;
        }

        if ("789".equals(context.getOrderId())) {
            orderSateInDataBase = OrderState.WAITING_SIGNED;
        }
        context.setSource(orderSateInDataBase);
        if (!context.getTransition().getSource().equals(orderSateInDataBase)) {
            System.out.println("当前状态不能执行该event");
            throw new RuntimeException("无效操作");
        }
        System.out.println("state will change from " + context.getSource() + " to " + context.getTarget());
    }

    public abstract void action(RepairStateContext<T> context);


    @Override
    public void execute(RepairStateContext<T> context) {
        preAction(context);
        action(context);
    }


}
