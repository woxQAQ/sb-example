package com.woxqaq.im.common.res;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BaseResponse<T> implements Serializable {
    private int code;

    private String message;

    private String reqId;

    private T data;

    public BaseResponse(int code, String message, String reqId, T data) {
        this.code = code;
        this.message = message;
        this.reqId = reqId;
        this.data = data;
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(int code, String message, String reqId) {
        this.code = code;
        this.message = message;
        this.reqId = reqId;
    }

}
