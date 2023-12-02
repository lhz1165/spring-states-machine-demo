package com.lhz.controller;

import com.alibaba.cola.statemachine.StateMachine;
import com.lhz.enums.OrderEvent;
import com.lhz.enums.OrderStatusEnum;
import com.lhz.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laihz
 * @date 2023/12/2 9:50
 */
@RestController
public class OrderOperaController {

    @Autowired
    @Qualifier("orderOperaMachine")
    StateMachine<OrderStatusEnum, OrderEvent, Order> orderOperaMachine;

    /**
     * 场景1-用户关闭订单
     *
     * @return {@link Boolean}
     */
    @PostMapping("userclose")
    public Boolean userCloseOrder() {
        try {
            String machineId = orderOperaMachine.getMachineId();
            System.out.println(machineId);
            Order order = Order.builder().orderId(1).orderName("用户").orderStatusEnum(OrderStatusEnum.INIT.name()).build();
            OrderStatusEnum orderStatusEnum = orderOperaMachine.fireEvent(OrderStatusEnum.INIT,OrderEvent.USER_CLOSE, order);
            System.out.println(orderStatusEnum.toString());
        } catch (Exception e) {
            return false;
        }
        //把订单状态改为关闭
        return true;
    }

    /**
     * 场景2-管理员关闭订单
     *
     * @return {@link Boolean}
     */
    @PostMapping("adminClose")
    public Boolean adminCloseOrder() {
        //把订单状态改为关闭
        Order order = Order.builder().orderId(1).orderName("后台操作人员").orderStatusEnum(OrderStatusEnum.HAVE_BEEN_DELIVERY.name()).build();
        OrderStatusEnum orderStatusEnum = orderOperaMachine.fireEvent(OrderStatusEnum.HAVE_BEEN_DELIVERY, OrderEvent.ADMIN_CLOSE, order);
        System.out.println(orderStatusEnum.toString());

        return true;
    }

    /**
     * 场景3-超时关闭订单
     *
     * @return {@link Boolean}
     */
    @PostMapping("overTimeclose")
    public Boolean overTimeCloseOrder() {
        //把订单状态改为关闭
        Order order = Order.builder().orderId(1).orderName("超时了关闭订单").orderStatusEnum(OrderStatusEnum.WAITING_FOR_DELIVERY.name()).build();
        //OrderStatusEnum orderStatusEnum = orderOperaMachine.fireEvent(OrderStatusEnum.CLOSE, OrderEvent.OVERTIME_CLOSE, order);
        OrderStatusEnum orderStatusEnum = orderOperaMachine.fireEvent(OrderStatusEnum.WAITING_FOR_DELIVERY, OrderEvent.OVERTIME_CLOSE, order);
        System.out.println(orderStatusEnum.toString());
        return true;
    }

    /**
     * 场景4-检查错误关闭订单
     *
     * @return {@link Boolean}
     */
    @PostMapping("checkErrorClose")
    public Boolean checkErrorCloseOrder() {
        //把订单状态改为关闭
        Order order = Order.builder().orderId(1).orderName("上级检查错误").orderStatusEnum(OrderStatusEnum.WAITING_FOR_DELIVERY.name()).build();
        OrderStatusEnum orderStatusEnum = orderOperaMachine.fireEvent(OrderStatusEnum.WAITING_FOR_DELIVERY, OrderEvent.CHECK_ERROR_CLOSE, order);
        System.out.println(orderStatusEnum.toString());
        return true;
    }
}
