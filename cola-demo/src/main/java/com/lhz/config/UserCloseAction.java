package com.lhz.config;

import com.alibaba.cola.statemachine.Action;
import com.lhz.enums.OrderEvent;
import com.lhz.enums.OrderStatusEnum;
import com.lhz.mapper.OrderMapper;
import com.lhz.mapper.UserMapper;
import com.lhz.pojo.Order;
import com.lhz.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author laihz
 * @date 2023/12/2 9:50
 */
@Component
public class UserCloseAction implements Action<OrderStatusEnum, OrderEvent, Order> {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void execute(OrderStatusEnum from, OrderStatusEnum to, OrderEvent event, Order order) {
        User u = new User();
        u.setName("hello");
        userMapper.insert(u);
        Order order1 = Order.builder().orderStatusEnum(OrderStatusEnum.INIT.name()).build();
        orderMapper.insert(order1);
        throw new RuntimeException("ss");


    }
}