package com.demo.util.states.enums;

import lombok.Getter;

/**
 * @author laihz
 * @date 2023/11/28 9:55
 */
@Getter
public enum OrderEvent {
    SIGN("10","签收"),
    REFUSE_SIGN("11","拒签"),

    REFUSE_SIGN_REVIEW_PASS("20","拒签通过"),
    REFUSE_SIGN_REVIEW_REJECT("21","拒签不通过"),

    PROCESS_START("30","开始处理"),
    SUSPEND("31","挂起"),
    SUSPEND_REVIEW_PASS("32","挂起复核通过"),
    SUSPEND_REVIEW_REJECT("33","挂起复核不通过"),
    SUSPEND_MANUAL_UNDO("34","手动撤销挂起"),
    SUSPEND_AUTO_UNDO("35","自动撤销挂起"),

    PROCESS_REVIEW_PASS("40","处理复核通过"),
    PROCESS_REVIEW_REJECT("41","处理复核不通过"),
    PROCESS_REVIEW_AUTO_PASS("","自动处理复核通过"),
    PROCESS_FINISH("42","处理完成"),

    ;
    String code;
    String desc;

    OrderEvent(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
