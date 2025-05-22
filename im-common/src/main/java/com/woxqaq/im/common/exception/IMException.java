package com.woxqaq.im.common.exception;

import com.woxqaq.im.common.enums.StatusEnum;

public class IMException extends GenericException{
    public IMException(String errMsg){
        super(errMsg);
        this.errMsg = errMsg;
    }   

    public IMException(String errMsg, String errCode){
        super(errMsg);
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public IMException(Exception e, String errMsg, String errCode){
        super(e, errMsg);
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public IMException(Exception oriEx){
        super(oriEx);
    }

    public IMException(Throwable cause){
        super(cause);
    }

    public IMException(String message, Exception oriEx){
        super(message, oriEx);
    }

    public IMException(String message, Throwable oriEx){
        super(message, oriEx);
    }

    public IMException(StatusEnum statusEnum) {
        super(statusEnum.getDesc());
        this.errCode = Integer.toString(statusEnum.getCode());
        this.errMsg = statusEnum.getDesc();
    }

    public IMException(StatusEnum statusEnum, String errMsg) {
        super(errMsg);
        this.errCode = Integer.toString(statusEnum.getCode());
        this.errMsg = errMsg;
    }

}
