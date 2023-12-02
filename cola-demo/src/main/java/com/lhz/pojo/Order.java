package com.lhz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author laihz
 * @date 2023/12/2 9:48
 */
@Data
@Builder
@TableName("`order`")
public class Order {
    @TableField(value = "state")
    public String orderStatusEnum;

    @TableId(value = "id",type = IdType.AUTO)
    public Integer orderId;

    @TableField(exist = false)
    public String orderName;

}
