package com.lhz.controller;

import com.lhz.enums.OrderEvents;
import com.lhz.enums.OrderStates;
import com.lhz.pojo.Order;
import com.lhz.service.OrderService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author laihz
 * @date 2023/11/27 19:34
 */
@Log4j2

@Api(value = "订单接口", tags = {"订单接口"})
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/get")
    public String get(@RequestParam Long id) {
        Order order = orderService.get(id);
        return order.toString();
    }

    @PostMapping("/create")
    public String create() {
        Order order = orderService.create();
        return order.toString();
    }

    @PutMapping("/pay")
    public String pay(@RequestParam Long id) {
        StateMachine<OrderStates, OrderEvents> sm = orderService.pay(id);
        if (sm.hasStateMachineError()) {
            Exception e = (Exception)sm.getExtendedState().getVariables().get("exception");
            log.error("pay exception",e );
            return e.getMessage();
        }
        return "success";
    }

    @PutMapping("/fulfill")
    public String fulfill(@RequestParam Long id) {
        StateMachine<OrderStates, OrderEvents> sm = orderService.fulfill(id);
        if (sm.hasStateMachineError()) {
            Exception e = (Exception)sm.getExtendedState().getVariables().get("exception");
            log.error("fulfill exception",e );
            return e.getMessage();
        }

        return "success";
    }

    @PutMapping("/cancel")
    public String cancel(@RequestParam Long id) {
        StateMachine<OrderStates, OrderEvents> sm = orderService.cancel(id);
        if (sm.hasStateMachineError()) {
            Exception e = (Exception)sm.getExtendedState().getVariables().get("exception");
            log.error("cancel exception",e );
            return e.getMessage();
        }
        return "success";
    }



}
