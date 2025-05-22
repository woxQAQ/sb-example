package com.woxqaq.im.common.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    SUCCESS(2000, "Success"),
    SERVER_NOT_AVAILABLE(4000, "server not available"),
    VALIDATION_FAIL(3000, "invalid argument"),;

    private final int code;
    private final String desc;

    private StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
