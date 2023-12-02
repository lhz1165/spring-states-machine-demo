package com.demo.util.states.enums;

import lombok.Getter;

/**
 * @author laihz
 * @date 2023/11/28 9:55
 */
@Getter
public enum OrderState {
    WAITING_SIGNED("B","待签收"),
    REFUSE_SIGN_WAITING_REVIEW("X","拒签待复核"),
    WAITING_PROCESS("K","待处理"),
    PROCESSING("H","处理中"),
    SUSPEND_WAITING_REVIEW("G","挂起待复核"),
    SUSPENDED("F","已挂起"),
    PROCESSED_WAITING_REVIEW("I","已处理待复核"),
    PROCESSED("J","已处理")
    ;
    String code;
    String desc;

    OrderState(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
