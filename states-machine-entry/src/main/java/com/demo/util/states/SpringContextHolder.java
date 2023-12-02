package com.demo.util.states;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ApplicationContext 工具类
 * @author weiwj
 * @date 2021/1/18 17:03
 */
@Component
@Order(value = Integer.MIN_VALUE)
@Log4j2
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            log.info("applicationContext 对象为空！");
        }
        return applicationContext;
    }

    /**
     * 根据类类型获取bean
     * @author weiwj
     * @date 2021/1/18 17:08
     * @tag 1.0.0
     * @param clazz bean的类类型
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据bean名称和类类型获取bean
     * @author weiwj
     * @date 2021/1/18 17:08
     * @tag 1.0.0
     * @param name bean的名称
     * @param clazz bean的类类型
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name,clazz);
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     *
     * @author weiwj
     * @date 2021/1/18 17:09
     * @tag 1.0.0
     * @param applicationContext ApplicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("------------初始化 SpringContextHolder------------");
        SpringContextHolder.applicationContext = applicationContext;
    }
}