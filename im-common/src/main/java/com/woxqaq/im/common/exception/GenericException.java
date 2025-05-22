package com.woxqaq.im.common.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GenericException extends RuntimeException implements Serializable{
    String errCode;
    String errMsg;

    public GenericException(String errMsg){
        super(errMsg);
    }

    public GenericException(Exception oriEx) {
        super(oriEx);
    }

    public GenericException(Exception oriEx, String errMsg) {
        super(errMsg, oriEx);
    }

    public GenericException(Throwable cause){
        super(cause);
    }

    public GenericException(String message, Exception oriEx) {
        super(message, oriEx);
    }

    public GenericException(String message, Throwable oriEx) {
        super(message, oriEx);
    }

}
