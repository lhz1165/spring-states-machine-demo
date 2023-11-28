package com.lhz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhz.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author laihz
 * @date 2023/11/28 9:33
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
