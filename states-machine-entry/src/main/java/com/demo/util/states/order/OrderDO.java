package com.demo.util.states.order;

import com.demo.util.states.enums.OrderState;
import lombok.Data;

/**
 * @author laihz
 * @date 2023/12/1 10:08
 */
@Data
public class OrderDO {
    String id;
    String state;


    public OrderState getOrderStateEnum() {
        OrderState orderState = OrderState.valueOf(getState());
        return orderState;
    }
}
