package com.lhz.enums;

/**
 * @author laihz
 * @date 2023/12/2 9:48
 */
public enum OrderStatusEnum {
    INIT("0", "待付款"),
    WAITING_FOR_DELIVERY("1", "待发货"),
    HAVE_BEEN_DELIVERY("2", "已发货"),
    CLOSE("3", "已取消");


    private final String code;
    private final String info;

    OrderStatusEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
