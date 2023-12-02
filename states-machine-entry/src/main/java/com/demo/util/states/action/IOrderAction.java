package com.demo.util.states.action;


import com.demo.util.states.RepairStateContext;

/**
 * @author laihz
 * @date 2023/11/30 14:44
 */
public interface IOrderAction<T> {
    void execute(RepairStateContext<T> context);

}
