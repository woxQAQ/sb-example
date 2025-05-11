package com.woxqaq.im.common.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    SUCCESS(2000, "Success");

    private final int code;
    private final String desc;

    private StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
