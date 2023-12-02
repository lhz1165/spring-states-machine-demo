package com.lhz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author laihz
 * @date 2023/12/2 10:54
 */
@Data
@TableName(value = ("users"))
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;

    String name;
}
