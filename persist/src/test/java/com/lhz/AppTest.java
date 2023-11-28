package com.lhz;

import com.lhz.mapper.OrderMapper;
import com.lhz.pojo.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest{
    @Resource
    OrderMapper mapper;

    @Test
    public void test() {

        mapper.insert(new Order("E1"));


    }

}
