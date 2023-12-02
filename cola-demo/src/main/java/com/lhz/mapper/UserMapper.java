package com.lhz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhz.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author laihz
 * @date 2023/12/2 10:56
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
