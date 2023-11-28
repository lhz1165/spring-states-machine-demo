package com.lhz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author laihz
 * @date 2023/11/27 19:33
 */
@TableName("`order`")
public class Order {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField("`state`")
    String state;

    public Order(String state) {
        this.state = state;
    }

    public Order(Integer id, String state) {
        this.id = id;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", state=" + state + "]";
    }
}
