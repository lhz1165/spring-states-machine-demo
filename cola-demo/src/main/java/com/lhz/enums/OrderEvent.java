package com.lhz.enums;

/**
 * @author laihz
 * @date 2023/12/2 9:49
 */
public enum OrderEvent {
    /**
     * 用户关闭
     */
    USER_CLOSE("0", "用户取消"),
    /**
     * 管理员关闭
     */
    ADMIN_CLOSE("1", "后台取消"),
    /**
     * 超时关闭
     */
    OVERTIME_CLOSE("2", "超时取消"),
    /**
     * 检查错误关闭
     */
    CHECK_ERROR_CLOSE("3", "上级审核取消"),

    /**
     * 用户付费
     */
    USER_PAY("4", "用户支付");


    /**
     * 密码
     */
    private final String code;
    /**
     * 信息
     */
    private final String info;

    /**
     * 订单事件
     *
     * @param code 密码
     * @param info 信息
     */
    OrderEvent(String code, String info) {
        this.code = code;
        this.info = info;
    }

    /**
     * 获取代码
     *
     * @return {@link String}
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取信息
     *
     * @return {@link String}
     */
    public String getInfo() {
        return info;
    }
}
