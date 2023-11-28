package com.lhz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lhz.enums.OrderStates;

/**
 * @author laihz
 * @date 2023/11/27 19:33
 */
@TableName("`order`")
public class Order {
    @TableId(value = "id",type = IdType.AUTO)
    Long id;
    @TableField("`state`")
    String state;

    public Order(String state) {
        this.state = state;
    }

    public Order(Long id, String state) {
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStates getState() {
        return OrderStates.valueOf(state);
    }

    public void setState(OrderStates state) {
        this.state = state.name();
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", state=" + state + "]";
    }
}
